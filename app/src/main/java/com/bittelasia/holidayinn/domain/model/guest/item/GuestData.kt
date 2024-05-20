package com.bittelasia.holidayinn.domain.model.guest.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GuestData(
    val assign_id: String,
    val birthdate: String,
    val checkin: String,
    val class_name: String,
    val device_id: String,
    val email: String,
    val firstname: String,
    val gs: String,
    val home_addr1: String,
    val home_addr2: String,
    val home_addr3: String,
    val home_addr4: String,
    @PrimaryKey
    val id: String,
    val landline_no: String,
    val lastname: String,
    val mobile_no: String,
    val reserve_no: String,
    val room_number: String,
    val weather_id: String
)

