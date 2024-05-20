package com.bittelasia.holidayinn.domain.model.channel.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel")
data class ChannelData(
    val channel_category_id: String?,
    val channel_description: String?,
    val channel_image: String?,
    val channel_programs: String?,
    val channel_title: String?,
    val channel_uri: String?,
    val enabled: String?,
    @PrimaryKey
    val id: String
)