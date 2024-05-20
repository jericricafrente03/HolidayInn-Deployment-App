package com.bittelasia.holidayinn.di

import com.bittelasia.holidayinn.data.local.MeshDataBase
import com.bittelasia.holidayinn.data.remote.MeshApi
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.usecases.MeshUsesCases
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMeshListRepository(
        meshDB: MeshDataBase,
        apiService: MeshApi,
    ): MeshListApiImpl {
        return MeshListApiImpl(
            meshDataBase = meshDB,
            api = apiService
        )
    }

    @Provides
    @Singleton
    fun provideMeshCases(repository: MeshListApiImpl): MeshUsesCases {
        return MeshUsesCases(
            getThemesResult = GetThemesResult(repository),
            getConfigResult = GetConfigResult(repository),
            getAppItem = GetAppItem(repository),
            getWeatherResult = GetWeatherResult(repository),
            getTimeResult = GetTimeResult(repository),
            getMessageResult = GetMessageResult(repository),
            readMessageResult = GetReadMessageResult(repository),
            getFacilitiesResult = GetFacilitiesResult(repository),
            getFacilitiesCatResult = GetFacilitiesCatResult(repository),
            getThemesBGResult = GetThemesBGResult(repository),
            getChannelResult = GetChannelResult(repository),
            getOnlineDevices = GetOnlineDevices(repository),
            postStatistics = PostStatistics(repository),
            guest = GetGuestResult(repository),
            getConciergeResult = GetConciergeResult(repository),
            getResult = GetRegistrationResult(repository),
            getLicense = GetLicenseResult(repository),
            showBroadcast = ShowBroadcast(repository),
            getSingleMessage = GetSingleMessage(repository),
            getSelectedFacilities = GetSelectedFacilities(repository)
        )
    }
}