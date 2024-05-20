package com.bittelasia.holidayinn.presentation.register.presenter

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.holidayinn.data.local.BaseUrlInterceptor
import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.stbpref.data.License
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.data.repository.stbpref.manager.LicenseDataStore.saveLicense
import com.bittelasia.holidayinn.data.repository.stbpref.manager.STBDataStore.saveSTB
import com.bittelasia.holidayinn.domain.model.license.item.LicenseData
import com.bittelasia.holidayinn.domain.model.license.item.LicenseKey
import com.bittelasia.holidayinn.domain.model.stb.StbRegistration
import com.bittelasia.holidayinn.domain.usecases.MeshUsesCases
import com.bittelasia.holidayinn.presentation.register.data.RegistrationFormEvent
import com.bittelasia.holidayinn.presentation.register.data.RegistrationFormState
import com.bittelasia.holidayinn.presentation.register.validation.ValidateIp
import com.bittelasia.holidayinn.presentation.register.validation.ValidatePort
import com.bittelasia.holidayinn.presentation.register.validation.ValidateRoom
import com.bittelasia.holidayinn.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val app: Application,
    private val cases: MeshUsesCases
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validateIp: ValidateIp = ValidateIp()
    private val validatePort: ValidatePort = ValidatePort()
    private val validateRoom: ValidateRoom = ValidateRoom()

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _progressStatus = MutableStateFlow(false)
    val progressStatus: StateFlow<Boolean> = _progressStatus

    private val _pingResult = MutableStateFlow("")
    val pingResult: StateFlow<String> = _pingResult

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.IpChanged -> {
                state = state.copy(ip = event.ip)
            }
            is RegistrationFormEvent.PortChange -> {
                state = state.copy(port = event.port)
            }
            is RegistrationFormEvent.RoomChanged -> {
                state = state.copy(room = event.room)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val ipResult = validateIp.getIpErrorIdOrNull(state.ip)
        val roomResult = validateRoom.getRoomErrorIdOrNull(state.room)
        val portResult = validatePort.getPortErrorIdOrNull(state.port)

        val hasError = listOf(
            ipResult,
            roomResult,
            portResult
        ).any {
            !it.successful
        }
        if (hasError) {
            state = state.copy(
                ipErr = ipResult.errorMessage,
                portErr = portResult.errorMessage,
                roomErr = roomResult.errorMessage
            )
            return
        }
        validateData(app)
    }
    fun pingIPAddress(ipAddress: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _progressStatus.value = true
                val process = Runtime.getRuntime().exec("/system/bin/ping -c 4 $ipAddress")
                val reader = BufferedReader(InputStreamReader(process.inputStream))
                val log = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    log.append(line + "\n")
                }
                process.waitFor()
                _progressStatus.value = false
                _pingResult.value = log.toString()
            } catch (e: Exception) {
                _pingResult.value = "Failed to ping: ${e.message}"
            }
        }
    }

    private fun validateData(app: Application) {
        viewModelScope.launch {
            try {
                val mac = async {
                    return@async NetworkUtil.macAddress(app)
                }
                val key = async {
                    return@async  LicenseData(LicenseKey(NetworkUtil.hashedDeviceMac(app)))
                }
                app.saveSTB(STB.apply {
                    HOST = "http://${state.ip}"
                    PORT = state.port
                    ROOM = state.room
                    MAC_ADDRESS = mac.await()
                    API_KEY = key.await().key.apiKey
                })
                BaseUrlInterceptor.setBaseUrl("${STB.HOST}:${STB.PORT}/")
                cases.getResult(
                    user = StbRegistration(
                        ip = STB.HOST,
                        port = STB.PORT,
                        room = STB.ROOM,
                        mac = STB.MAC_ADDRESS,
                        apiKey = STB.API_KEY
                    )
                ).onEach { result ->
                    if (result.data != null){
                        result.data?.data?.let { reason ->
                            when (result) {
                                is DataState.Loading -> {
                                    validationEventChannel.send(ValidationEvent.Loading)
                                }
                                is DataState.Success -> {
                                    when (reason.result) {
                                        "success" -> {
                                            cases.getLicense(app).collectLatest { license ->
                                                app.apply {
                                                    saveLicense(License.apply {
                                                        license.data?.data?.let { getData ->
                                                            STATUS = getData.result
                                                            END_DATE = getData.endDate
                                                            REMAINING_DAYS = getData.remaining_days
                                                        }
                                                    })
                                                    saveSTB(STB.apply {
                                                        USERNAME = result.data!!.jid_user
                                                        PASSWORD = result.data!!.jid_pass
                                                        DEV_ID = result.data!!.device_id
                                                        FIRST_RUN = "1"
                                                    })
                                                }
                                            }
                                            validationEventChannel.send(ValidationEvent.Success)
                                        }
                                        "failed" -> {
                                            validationEventChannel.send(ValidationEvent.Error(reason.reasons))
                                        }
                                    }
                                }
                                is DataState.Error -> {
                                    validationEventChannel.send(ValidationEvent.Error(reason.reasons))
                                }
                            }
                        }
                    }else if (result.message != null){
                        validationEventChannel.send(ValidationEvent.Error(result.message))
                    }
                }.launchIn(viewModelScope)
            } catch (e: Exception) {
                validationEventChannel.send(ValidationEvent.Error())
            }
        }
    }

    sealed class ValidationEvent {
        data object Loading : ValidationEvent()
        data object Success : ValidationEvent()
        class Error(val messageId: String = "Couldn't Register") : ValidationEvent()
    }
}