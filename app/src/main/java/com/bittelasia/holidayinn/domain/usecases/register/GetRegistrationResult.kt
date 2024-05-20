package com.bittelasia.holidayinn.domain.usecases.register

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.result.Register
import com.bittelasia.holidayinn.domain.model.stb.StbRegistration
import kotlinx.coroutines.flow.Flow

class GetRegistrationResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(user: StbRegistration): Flow<DataState<Register>> {
        return repository.registerResult(user)
    }
}