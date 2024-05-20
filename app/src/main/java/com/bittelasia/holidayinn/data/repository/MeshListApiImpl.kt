package com.bittelasia.holidayinn.data.repository

import android.content.Context
import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.local.MeshDataBase
import com.bittelasia.holidayinn.data.remote.MeshApi
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import com.bittelasia.holidayinn.domain.model.broadcast.BroadCastData
import com.bittelasia.holidayinn.domain.model.channel.item.ChannelData
import com.bittelasia.holidayinn.domain.model.concierge.item.ConciergeData
import com.bittelasia.holidayinn.domain.model.config.item.SystemData
import com.bittelasia.holidayinn.domain.model.facilities.category.FacilitiesCategoryData
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import com.bittelasia.holidayinn.domain.model.guest.item.GuestData
import com.bittelasia.holidayinn.domain.model.license.item.LicenseData
import com.bittelasia.holidayinn.domain.model.license.item.LicenseKey
import com.bittelasia.holidayinn.domain.model.license.response.LicenseDate
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import com.bittelasia.holidayinn.domain.model.message.readmessage.ReadMessage
import com.bittelasia.holidayinn.domain.model.post_stat.InputStatistics
import com.bittelasia.holidayinn.domain.model.post_stat.StatResponse
import com.bittelasia.holidayinn.domain.model.result.Register
import com.bittelasia.holidayinn.domain.model.stb.StbRegistration
import com.bittelasia.holidayinn.domain.model.theme.item.Theme
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.domain.model.time.Time
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.holidayinn.domain.repository.MeshListApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.NoRouteToHostException
import javax.inject.Inject

class MeshListApiImpl @Inject constructor(
    private val meshDataBase: MeshDataBase,
    private val api: MeshApi
) : MeshListApi {

    override fun themesResult(): Flow<DataState<List<Zone>>> = flow {
        val dataBase = meshDataBase.zoneDao()
        try {
            emit(DataState.Loading())
            val result = api.getThemes()
            dataBase.deleteAllZones()
            dataBase.insertZones(result.zones)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllZones()
        emit(DataState.Success(dbResult))
    }

    override fun themesBgResult(): Flow<DataState<Theme>> = flow {
        val dataBase = meshDataBase.themeDao()
        try {
            emit(DataState.Loading())
            val result = api.getThemes()
            dataBase.deleteAllThemes()
            result.bg?.let {
                dataBase.insertThemes(Theme(result.bg))
            }
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllThemes()
        emit(DataState.Success(dbResult))
    }

    override fun configResult(): Flow<DataState<SystemData>> = flow {
        val dataBase = meshDataBase.configDao()
        try {
            emit(DataState.Loading())
            val result = api.getConfig()
            dataBase.deleteAllConfig()
            dataBase.insertConfig(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllConfig()
        emit(DataState.Success(dbResult))
    }

    override fun uiIconResult(): Flow<DataState<List<AppData>>> = flow {
        val dataBase = meshDataBase.appDataDao()
        try {
            emit(DataState.Loading())
            val result = api.getIptvUI()
            dataBase.deleteAllAppDataItems()
            dataBase.insertAppData(result.apps)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllAppDataItems()
        emit(DataState.Success(dbResult))
    }

    override fun weatherResult(): Flow<DataState<List<GetWeeklyWeatherForecastData>>> = flow {
        val dataBase = meshDataBase.weatherDao()
        try {
            emit(DataState.Loading())
            val result = api.getWeather()
            dataBase.deleteAllWeeklyWeather()
            dataBase.insertWeeklyWeather(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllWeeklyWeather()
        emit(DataState.Success(dbResult))
    }

    override fun messageResult(): Flow<DataState<List<GetMessageData>>> = flow {
        val dataBase = meshDataBase.getMessageDao()
        try {
            emit(DataState.Loading())
            val result = api.getMessage()
            dataBase.deleteAllMessages()
            dataBase.insertMessages(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllMessages()
        emit(DataState.Success(dbResult))
    }

    override suspend fun guestMessage(id: String) = flow {
        val dataBase = meshDataBase.getMessageDao()
        try {
            val dbResult = dataBase.getMessage(id = id)
           emit(dbResult)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun facilitiesResult(): Flow<DataState<List<FacilitiesData>>> = flow {
        val dataBase = meshDataBase.facilitiesDao()
        try {
            emit(DataState.Loading())
            val result = api.getFacilities()
            dataBase.deleteAllFacilities()
            dataBase.insertFacilities(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllFacilities()
        emit(DataState.Success(dbResult))
    }

    override suspend fun selectedInfo(id: String) = flow {
        val dataBase = meshDataBase.facilitiesDao()
        try {
            val dbResult = dataBase.getHotelInfo(id = id)
            emit(dbResult)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun facilitiesCategoryResult(): Flow<DataState<List<FacilitiesCategoryData>>> = flow {
        val dataBase = meshDataBase.facilitiesCategoryDao()
        try {
            emit(DataState.Loading())
            val result = api.getFacilitiesCategory()
            dataBase.deleteFacilitiesCategories()
            dataBase.insertFacilitiesCategories(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getFacilitiesCategories()
        emit(DataState.Success(dbResult))
    }

    override fun channelResult(): Flow<DataState<List<ChannelData>>> = flow {
        val dataBase = meshDataBase.channelDao()
        try {
            emit(DataState.Loading())
            val result = api.getChannel()
            dataBase.deleteAllChannels()
            dataBase.insertChannels(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllChannels()
        emit(DataState.Success(dbResult))
    }

    override fun readMessageResult(id: String): Flow<DataState<ReadMessage>> = flow {
        emit(DataState.Loading())
        try {
            val result = api.getReadMessage(id = id)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

    override fun time(): Flow<DataState<Time>> = flow {
        emit(DataState.Loading())
        try {
            val result = api.getTime()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

    override fun showOnline(): Flow<DataState<String>> = flow{
        emit(DataState.Loading())
        try {
            val result = api.showOnline()
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }
    override fun showBroadCast(): Flow<DataState<BroadCastData>> = flow{
        emit(DataState.Loading())
        try {
            val result = api.getBroadcast()
            emit(DataState.Success(result.data))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

    override fun postStatistics(inputStatistics: InputStatistics): Flow<DataState<StatResponse>> = flow {
        emit(DataState.Loading())
        try {
            val result = api.postStat(inputStatistics)
            emit(DataState.Success(result.body()))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

    override fun guestResult(): Flow<DataState<GuestData>> = flow{
        val dataBase = meshDataBase.getGuestDao()
        try {
            emit(DataState.Loading())
            val result = api.getCustomer()
            dataBase.deleteGuest()
            dataBase.insertGuest(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getGuest()
        emit(DataState.Success(dbResult))
    }

    override fun conciergeResult(): Flow<DataState<List<ConciergeData>>> = flow {
        val dataBase = meshDataBase.getConciergeDao()
        try {
            emit(DataState.Loading())
            val result = api.getConcierge()
            dataBase.deleteAllConciergeDataItems()
            dataBase.insertConciergeData(result.data)
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
        val dbResult = dataBase.getAllConciergeDataItems()
        emit(DataState.Success(dbResult))
    }

    override fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>> =
        flow {
            try {
                emit(DataState.Loading())
                stbRegistration.apply {
                    val registerUser = api.registerResult(
                        apiKey = STB.API_KEY,
                        mcAddress = mac,
                        room = room
                    )
                    emit(DataState.Success(registerUser))
                }
            } catch (e: Exception) {
                when(e) {
                    is NoRouteToHostException,
                    is HttpException,
                    is IOException -> {
                        emit(DataState.Error(message = e.message ?: "Error"))
                    }
                    else -> throw e
                }
            }
        }

    override suspend fun postResult(app: Context): Flow<DataState<LicenseDate>> = flow {
        try {
            emit(DataState.Loading())
            val key = LicenseData(LicenseKey(STB.API_KEY))
            val getData = api.getLicense(key)
            emit(DataState.Success(getData.body()))
        } catch (e: Exception) {
            emit(DataState.Error(message = e.message ?: "Error"))
        }
    }

}