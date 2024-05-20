package com.bittelasia.holidayinn.domain.model.facilities.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facilities_category")
data class FacilitiesCategoryData(
    val category_description: String,
    val category_name: String,
    @PrimaryKey
    val id: String,
    val img_preview: String,
    val img_uri: String,
    val order_no: String
)