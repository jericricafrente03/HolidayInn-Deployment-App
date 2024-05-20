package com.bittelasia.holidayinn.domain.usecases.facilties

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import kotlinx.coroutines.flow.Flow

class GetFacilitiesResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<FacilitiesData>>> {
        return repository.facilitiesResult()
    }
}