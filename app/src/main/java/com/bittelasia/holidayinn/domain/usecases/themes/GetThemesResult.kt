package com.bittelasia.holidayinn.domain.usecases.themes

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import kotlinx.coroutines.flow.Flow

class GetThemesResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<Zone>>> {
        return repository.themesResult()
    }
}