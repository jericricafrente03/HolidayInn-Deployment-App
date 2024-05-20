package com.bittelasia.holidayinn.domain.model.app_item.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppData(
    val app: String?,
    val app_type: String?,
    val display_name: String?,
    val icon: String?,
    val icon_selected: String?,
    @PrimaryKey
    val id: String,
    val method: String?
)