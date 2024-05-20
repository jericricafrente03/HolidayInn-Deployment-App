package com.bittelasia.holidayinn.domain.usecases.facilties

import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.facilities.item.FacilitiesData
import kotlinx.coroutines.flow.Flow

class GetSelectedFacilities(
    private val repository: MeshListApiImpl
) {
    suspend operator fun invoke(id: String): Flow<FacilitiesData> {
        return repository.selectedInfo(id = id)
    }
}