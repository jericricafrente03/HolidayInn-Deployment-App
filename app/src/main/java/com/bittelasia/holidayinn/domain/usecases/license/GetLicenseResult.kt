package com.bittelasia.holidayinn.domain.usecases.license

import android.content.Context
import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.license.response.LicenseDate
import kotlinx.coroutines.flow.Flow

class GetLicenseResult(
    private val repository: MeshListApiImpl
) {
    suspend operator fun invoke(context: Context): Flow<DataState<LicenseDate>> {
        return repository.postResult(context)
    }
}