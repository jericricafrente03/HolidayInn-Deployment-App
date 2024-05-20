package com.bittelasia.holidayinn.domain.usecases.message

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import kotlinx.coroutines.flow.Flow

class GetMessageResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<GetMessageData>>> {
        return repository.messageResult()
    }
}