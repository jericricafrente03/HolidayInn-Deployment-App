package com.bittelasia.holidayinn.domain.usecases.appitem

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData
import kotlinx.coroutines.flow.Flow

class GetAppItem(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<AppData>>> {
        return repository.uiIconResult()
    }
}