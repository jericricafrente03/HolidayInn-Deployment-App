package com.bittelasia.holidayinn.domain.model.channel.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.channel.item.ChannelData

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channel")
    fun getAllChannels(): List<ChannelData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<ChannelData>)

    @Query("DELETE FROM channel")
    suspend fun deleteAllChannels()
}