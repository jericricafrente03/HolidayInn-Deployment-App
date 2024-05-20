package com.bittelasia.holidayinn.domain.usecases.broadcast

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.broadcast.BroadCastData
import com.bittelasia.holidayinn.domain.model.time.Time
import kotlinx.coroutines.flow.Flow

class ShowBroadcast(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<BroadCastData>> {
        return repository.showBroadCast()
    }
}