package com.bittelasia.holidayinn.domain.model.config.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class SystemData(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val checkout_message: String?,
    val city: String?,
    val country: String?,
    val currency: String?,
    val description: String?,
    val email: String?,
    val logo: String?,
    val map_coord: String?,
    val max_idle: String?,
    val name: String?,
    val reservation_message: String?,
    val service_request_message: String?,
    val street: String?,
    val timezone_id: String?,
    val weather_id: String?,
    val website: String?,
    val welcome_message: String?
)