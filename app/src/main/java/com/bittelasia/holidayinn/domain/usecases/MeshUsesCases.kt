package com.bittelasia.holidayinn.domain.usecases

import com.bittelasia.holidayinn.domain.usecases.appitem.GetAppItem
import com.bittelasia.holidayinn.domain.usecases.broadcast.ShowBroadcast
import com.bittelasia.holidayinn.domain.usecases.channel.GetChannelResult
import com.bittelasia.holidayinn.domain.usecases.concierge.GetConciergeResult
import com.bittelasia.holidayinn.domain.usecases.config.GetConfigResult
import com.bittelasia.holidayinn.domain.usecases.facilties.GetFacilitiesCatResult
import com.bittelasia.holidayinn.domain.usecases.facilties.GetFacilitiesResult
import com.bittelasia.holidayinn.domain.usecases.facilties.GetSelectedFacilities
import com.bittelasia.holidayinn.domain.usecases.guest.GetGuestResult
import com.bittelasia.holidayinn.domain.usecases.license.GetLicenseResult
import com.bittelasia.holidayinn.domain.usecases.message.GetMessageResult
import com.bittelasia.holidayinn.domain.usecases.message.GetReadMessageResult
import com.bittelasia.holidayinn.domain.usecases.message.GetSingleMessage
import com.bittelasia.holidayinn.domain.usecases.post_stat.PostStatistics
import com.bittelasia.holidayinn.domain.usecases.register.GetRegistrationResult
import com.bittelasia.holidayinn.domain.usecases.show_online.GetOnlineDevices
import com.bittelasia.holidayinn.domain.usecases.themes.GetThemesBGResult
import com.bittelasia.holidayinn.domain.usecases.themes.GetThemesResult
import com.bittelasia.holidayinn.domain.usecases.time.GetTimeResult
import com.bittelasia.holidayinn.domain.usecases.weather.GetWeatherResult

data class MeshUsesCases(
    val getThemesResult: GetThemesResult,
    val getConfigResult: GetConfigResult,
    val getAppItem: GetAppItem,
    val getWeatherResult: GetWeatherResult,
    val getTimeResult: GetTimeResult,
    val getMessageResult: GetMessageResult,
    val getSingleMessage: GetSingleMessage,
    val readMessageResult: GetReadMessageResult,
    val getFacilitiesResult: GetFacilitiesResult,
    val getSelectedFacilities: GetSelectedFacilities,
    val getFacilitiesCatResult: GetFacilitiesCatResult,
    val getThemesBGResult: GetThemesBGResult,
    val getChannelResult: GetChannelResult,
    val getOnlineDevices: GetOnlineDevices,
    val postStatistics: PostStatistics,
    val guest: GetGuestResult,
    val getConciergeResult: GetConciergeResult,
    val getResult: GetRegistrationResult,
    val getLicense: GetLicenseResult,
    val showBroadcast: ShowBroadcast
)