package com.bittelasia.holidayinn.domain.model.theme.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zone")
data class Zone(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val section: String?,
    val text_color: String?,
    val text_selected: String?,
    val url: String?
)