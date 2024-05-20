package com.bittelasia.holidayinn.domain.usecases.concierge

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.concierge.item.ConciergeData
import kotlinx.coroutines.flow.Flow

class GetConciergeResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<ConciergeData>>> {
        return repository.conciergeResult()
    }
}