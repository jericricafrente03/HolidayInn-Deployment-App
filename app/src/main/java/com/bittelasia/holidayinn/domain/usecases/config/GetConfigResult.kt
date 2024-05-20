package com.bittelasia.holidayinn.domain.usecases.config

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.config.item.SystemData
import kotlinx.coroutines.flow.Flow

class GetConfigResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<SystemData>> {
        return repository.configResult()
    }
}