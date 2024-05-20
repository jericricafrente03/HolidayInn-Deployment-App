package com.bittelasia.holidayinn.domain.model.message.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "get_message")
data class GetMessageData(
    @PrimaryKey
    val id: String,
    val messg_datetime: String?,
    val messg_from: String?,
    val messg_status: String?,
    val messg_subject: String?,
    val messg_text: String?,
    val room_id: String?
)