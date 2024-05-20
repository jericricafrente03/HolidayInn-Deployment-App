package com.bittelasia.holidayinn.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.manager.Transmitter
import com.bittelasia.holidayinn.data.manager.XMPPManager
import com.bittelasia.holidayinn.data.repository.stbpref.data.License
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.data.repository.stbpref.manager.LicenseDataStore.saveLicense
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import com.bittelasia.holidayinn.domain.model.broadcast.BroadCastData
import com.bittelasia.holidayinn.domain.model.channel.item.ChannelData
import com.bittelasia.holidayinn.domain.model.config.item.SystemData
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import com.bittelasia.holidayinn.domain.model.post_stat.InputStatistics
import com.bittelasia.holidayinn.domain.model.post_stat.PostStatistics
import com.bittelasia.holidayinn.domain.model.theme.item.Theme
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.holidayinn.domain.usecases.MeshUsesCases
import com.bittelasia.holidayinn.util.ADB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val context: Application,
    private val meshUsesCases: MeshUsesCases
) : ViewModel() {

    // Use property delegation to simplify the creation of MutableStateFlow properties
    val selectedConfig = MutableStateFlow<SystemData?>(null)
    val selectedBg = MutableStateFlow<Theme?>(null)
    val selectedWeather = MutableStateFlow<GetWeeklyWeatherForecastData?>(null)
    val selectedChannel = MutableStateFlow<List<ChannelData>?>(null)
    val selectedAppItem = MutableStateFlow<List<AppData>?>(null)
    val selectedListWeather = MutableStateFlow<List<GetWeeklyWeatherForecastData>?>(null)
    val selectedMessageData = MutableStateFlow<List<GetMessageData>?>(null)
    val selectedMessageInfo = MutableStateFlow<GetMessageData?>(null)
    val selectedFac = MutableStateFlow<List<FacilitiesData>?>(null)
    val selectedFacImage = MutableStateFlow<FacilitiesData?>(null)

    val themeApplist = MutableStateFlow<Zone?>(null)
    val themeWeatherTop = MutableStateFlow<Zone?>(null)
    val themeWeatherBottom = MutableStateFlow<Zone?>(null)
    val themeGuest = MutableStateFlow<Zone?>(null)
    val themeMessageList = MutableStateFlow<Zone?>(null)
    val themeTimeWeather = MutableStateFlow<Zone?>(null)
    val themeMessageData = MutableStateFlow<Zone?>(null)
    val themeFacilitiesCat = MutableStateFlow<Zone?>(null)
    val themeFacilities = MutableStateFlow<Zone?>(null)
    val themeLogo = MutableStateFlow<Zone?>(null)
    val themeWelcome = MutableStateFlow<Zone?>(null)
    val broadcastReceiver = MutableStateFlow<BroadCastData?>(null)
    val messageNo = MutableStateFlow(0)

    init {
        meshAPIFlow()
    }

    fun allApiFlow() {
        val apis = listOf(
            ::getThemeUrl,
            ::getThemeBg,
            ::getAppItem,
            ::updateTime,
            ::configResult,
            ::getMessage
        )
        viewModelScope.launch(Dispatchers.IO) {
            apis.forEach { launch { it() } }
        }
    }

    private fun getThemeUrl() = apiFlowHandler {
        meshUsesCases.getThemesResult().filter { it.data != null }.collectLatest {
            it.data?.forEach { data ->
                updateThemeValue(data)
            }
        }
    }

    private fun getThemeBg() = apiFlowHandler {
        meshUsesCases.getThemesBGResult().filter { it.data != null }.collectLatest {
            selectedBg.value = it.data
        }
    }

    private fun getAppItem() = apiFlowHandler {
        meshUsesCases.getAppItem().filter { it.data != null }.collectLatest {
            selectedAppItem.value = it.data
        }
    }

    private fun configResult() = apiFlowHandler {
        meshUsesCases.getConfigResult().filter { it.data != null }.collectLatest {
            selectedConfig.value = it.data
        }
    }


    private fun updateTime() = apiFlowHandler {
        meshUsesCases.getTimeResult().filter { it.data != null }.collectLatest {
            it.data?.time?.let { setTime ->
                val command = "su 0 toybox date $setTime"
                ADB.exec(command)
            }
        }
    }


    fun getFacilities() = apiFlowHandler {
        meshUsesCases.getFacilitiesResult().filter { it.data != null }.collectLatest { ids ->
            selectedFac.value = ids.data
        }
    }


    fun getMessage() = apiFlowHandler {
        meshUsesCases.getMessageResult().filter { it.data != null }
            .map {
                val sortDate = it.data
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
                sortDate?.sortedByDescending { dates ->
                    dates.messg_datetime?.let { sort -> dateFormat.parse(sort) }
                }
            }
            .collectLatest {
                val unreadConversation = it?.filter { data -> data.messg_status != "2" }
                if (unreadConversation != null) {
                    messageNo.value = unreadConversation.size
                }
                selectedMessageData.value = it
            }
    }


    fun fetchWeather() = apiFlowHandler {
        meshUsesCases.getWeatherResult().filter { it.data != null }
            .collectLatest { w ->
                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val result = w.data?.filter { date ->
                    date.date != todayDate
                }?.take(6)
                val getWeather = w.data?.find { date ->
                    date.date == todayDate
                } ?: w.data?.last()
                selectedListWeather.value = result
                selectedWeather.value = getWeather
            }
    }


    private fun getResetLicense() = apiFlowHandler {
        meshUsesCases.getLicense(context).filter { it.data != null }.collectLatest { license ->
            context.apply {
                license.data?.data?.let { getData ->
                    saveLicense(License.apply {
                        STATUS = getData.result
                        END_DATE = getData.endDate
                        REMAINING_DAYS = getData.remaining_days
                    })
                }
            }
        }
    }


    private fun resetSTB() = apiFlowHandler {
        ADB.exec("pm clear com.bittelasia.vermillion")
    }


    private fun showSetting() = apiFlowHandler {
        ADB.exec("am start -n 'com.android.settings/.Settings'")
    }


    fun getChannel() = apiFlowHandler {
        meshUsesCases.getChannelResult().filter { it.data != null }.collectLatest {
            selectedChannel.value = it.data
            val allChannel = it.data?.map { channel ->
                channel.id.toInt()
            } ?: emptyList()
            getStatistics(listChannel = allChannel)
        }
    }
    fun guestMessage(id: String) = apiFlowHandler {
        meshUsesCases.getSingleMessage(id = id).collectLatest {
            selectedMessageInfo.value = it
            val filterMessage = it.messg_status == "1" || it.messg_status == "0"
            if (filterMessage){
                meshUsesCases.readMessageResult(id = id).collectLatest {
                    getMessage()
                }
            }
        }
    }
    fun selecteFacilities(id: String) = apiFlowHandler {
        meshUsesCases.getSelectedFacilities(id = id).collectLatest {
            selectedFacImage.value = it
        }
    }

    private fun getBroadcast() = apiFlowHandler {
        meshUsesCases.showBroadcast().filter { it.data != null }.collectLatest {
            broadcastReceiver.value = it.data
        }
    }


    private fun getStatistics(listChannel: List<Int>) = apiFlowHandler {
        val random = Random(System.currentTimeMillis())
        val sdfOutput = SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.getDefault())
        val dateLicence = sdfOutput.format(Date())
        if (listChannel.isNotEmpty()) {
            val data = Random.nextInt(1, listChannel.size)
            val randomList = List(5) {
                PostStatistics(
                    device_id = STB.DEV_ID,
                    start = dateLicence,
                    end = dateLicence,
                    id = random.nextInt(1, 99),
                    item_id = data,
                    item_type = "tv"
                )
            }
            meshUsesCases.postStatistics(InputStatistics(randomList)).collectLatest {}
        }
    }

    private fun showOnline() =
        apiFlowHandler {
            meshUsesCases.getOnlineDevices().collect {}
        }


    private fun updateThemeValue(data: Zone) {
        when (data.section) {
            "GuestAndRoomZone" -> themeGuest.value = data
            "TimeAndWeatherZone" -> themeTimeWeather.value = data
            "AppListZone" -> themeApplist.value = data
            "WeatherTodayZone" -> themeWeatherTop.value = data
            "WeatherForecastZone" -> themeWeatherBottom.value = data
            "MessageListZone" -> themeMessageList.value = data
            "MessageDisplayZone" -> themeMessageData.value = data
            "HotelInfoCategoryZone" -> themeFacilitiesCat.value = data
            "HotelInfoItemZone" -> themeFacilities.value = data
            "LogoZone" -> themeLogo.value = data
            "WelcomeMessageZone" -> themeWelcome.value = data
        }
    }

    private fun meshAPIFlow() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    XMPPManager.xmppData.collectLatest { xmppData ->
                        if (xmppData.isNotEmpty()) {
                            Log.v("meme", "XMPPManager -> $xmppData")
                            executeCommand(Transmitter.xmppToAPI(xmppData))
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun executeCommand(command: Transmitter) {
        when (command) {
            is Transmitter.Applist -> getAppItem()
            is Transmitter.Config -> configResult()
            is Transmitter.Themes -> {
                coroutineScope {
                    val response = listOf(
                        async { getThemeBg() },
                        async { getThemeUrl() }
                    )
                    response.awaitAll()
                }
            }
            is Transmitter.Television -> getChannel()
            is Transmitter.Message -> getMessage()
            is Transmitter.Facility -> getFacilities()
            is Transmitter.CheckStb -> showOnline()
            is Transmitter.Reset -> resetSTB()
            is Transmitter.Settings -> showSetting()
            is Transmitter.ResetLicense -> getResetLicense()
            is Transmitter.Broadcast -> getBroadcast()
        }
    }

    private fun apiFlowHandler(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("API Error", "Error during API call", e)
            }
        }
    }
}