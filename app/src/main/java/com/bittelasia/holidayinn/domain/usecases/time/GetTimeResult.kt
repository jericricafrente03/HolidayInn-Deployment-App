package com.bittelasia.holidayinn.domain.usecases.time

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.time.Time
import kotlinx.coroutines.flow.Flow

class GetTimeResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<Time>> {
        return repository.time()
    }
}