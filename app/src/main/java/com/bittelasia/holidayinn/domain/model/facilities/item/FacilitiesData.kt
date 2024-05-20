package com.bittelasia.holidayinn.domain.model.facilities.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facilities")
data class FacilitiesData(
    val category_id: String?,
    val img_uri: String?,
    val item_description: String?,
    val item_name: String?,
    val unit_price: String?,
    @PrimaryKey
    val id: String
)