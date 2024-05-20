package com.bittelasia.holidayinn.domain.model.concierge.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConciergeData(
    val address: String,
    val category_id: String,
    val contact_no: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val img_uri: String,
    val lat: String,
    val lon: String,
    val map_img: String,
    val name: String,
    val order_no: String,
    val qr_img: String,
    val rating: String
)