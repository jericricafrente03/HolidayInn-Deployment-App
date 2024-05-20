package com.bittelasia.holidayinn.domain.usecases.message

import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData
import kotlinx.coroutines.flow.Flow

class GetSingleMessage(
    private val repository: MeshListApiImpl
) {
    suspend operator fun invoke(id: String): Flow<GetMessageData> {
        return repository.guestMessage(id)
    }
}