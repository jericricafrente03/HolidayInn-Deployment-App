package com.bittelasia.holidayinn.domain.usecases.post_stat

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.post_stat.InputStatistics
import com.bittelasia.holidayinn.domain.model.post_stat.StatResponse
import kotlinx.coroutines.flow.Flow

class PostStatistics(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(inputStatistics: InputStatistics): Flow<DataState<StatResponse>> {
        return repository.postStatistics(inputStatistics)
    }
}