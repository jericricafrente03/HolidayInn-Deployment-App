package com.bittelasia.holidayinn.domain.model.message.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.message.item.GetMessageData

@Dao
interface GetMessageDao {
    @Query("SELECT * FROM get_message")
    fun getAllMessages(): List<GetMessageData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<GetMessageData>)

    @Query("DELETE FROM get_message")
    suspend fun deleteAllMessages()

    @Query("SELECT * FROM get_message WHERE id = :id")
    suspend fun getMessage(id: String): GetMessageData
}