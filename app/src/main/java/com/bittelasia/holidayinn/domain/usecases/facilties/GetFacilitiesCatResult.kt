package com.bittelasia.holidayinn.domain.usecases.facilties

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.facilities.category.FacilitiesCategoryData
import kotlinx.coroutines.flow.Flow

class GetFacilitiesCatResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<FacilitiesCategoryData>>> {
        return repository.facilitiesCategoryResult()
    }
}