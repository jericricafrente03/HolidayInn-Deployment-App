package com.bittelasia.holidayinn.domain.repository

import android.content.Context
import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import com.bittelasia.holidayinn.domain.model.broadcast.BroadCastData
import com.bittelasia.holidayinn.domain.model.channel.item.ChannelData
import com.bittelasia.holidayinn.domain.model.concierge.item.ConciergeData
import com.bittelasia.holidayinn.domain.model.config.item.SystemData
import com.bittelasia.holidayinn.domain.model.facilities.category.FacilitiesCategoryData
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import com.bittelasia.holidayinn.domain.model.guest.item.GuestData
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
import kotlinx.coroutines.flow.Flow

interface MeshListApi {
    fun themesResult(): Flow<DataState<List<Zone>>>
    fun themesBgResult(): Flow<DataState<Theme>>
    fun configResult(): Flow<DataState<SystemData>>
    fun uiIconResult(): Flow<DataState<List<AppData>>>
    fun weatherResult(): Flow<DataState<List<GetWeeklyWeatherForecastData>>>
    fun messageResult(): Flow<DataState<List<GetMessageData>>>
    suspend fun guestMessage(id: String): Flow<GetMessageData>
    fun facilitiesResult(): Flow<DataState<List<FacilitiesData>>>
    suspend fun selectedInfo(id: String): Flow<FacilitiesData>
    fun facilitiesCategoryResult(): Flow<DataState<List<FacilitiesCategoryData>>>
    fun channelResult(): Flow<DataState<List<ChannelData>>>
    fun readMessageResult(id: String): Flow<DataState<ReadMessage>>
    fun time(): Flow<DataState<Time>>
    fun showOnline(): Flow<DataState<String>>
    fun showBroadCast(): Flow<DataState<BroadCastData>>
    fun postStatistics(inputStatistics: InputStatistics): Flow<DataState<StatResponse>>
    fun guestResult(): Flow<DataState<GuestData>>
    fun conciergeResult(): Flow<DataState<List<ConciergeData>>>
    fun registerResult(stbRegistration: StbRegistration): Flow<DataState<Register>>
    suspend fun postResult(app: Context): Flow<DataState<LicenseDate>>
}