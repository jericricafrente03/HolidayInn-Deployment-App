package com.bittelasia.holidayinn.domain.usecases.message

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.message.readmessage.ReadMessage
import kotlinx.coroutines.flow.Flow

class GetReadMessageResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(id: String): Flow<DataState<ReadMessage>> {
        return repository.readMessageResult(id = id)
    }
}