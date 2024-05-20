package com.bittelasia.holidayinn.domain.usecases.guest

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.guest.item.GuestData
import kotlinx.coroutines.flow.Flow

class GetGuestResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<GuestData>> {
        return repository.guestResult()
    }
}