package com.bittelasia.holidayinn.domain.usecases.show_online

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import kotlinx.coroutines.flow.Flow

class GetOnlineDevices(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<String>> {
        return repository.showOnline()
    }
}