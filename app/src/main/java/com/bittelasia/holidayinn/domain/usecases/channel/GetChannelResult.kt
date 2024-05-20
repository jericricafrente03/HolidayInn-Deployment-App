package com.bittelasia.holidayinn.domain.usecases.channel

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.channel.item.ChannelData
import kotlinx.coroutines.flow.Flow

class GetChannelResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<ChannelData>>> {
        return repository.channelResult()
    }
}