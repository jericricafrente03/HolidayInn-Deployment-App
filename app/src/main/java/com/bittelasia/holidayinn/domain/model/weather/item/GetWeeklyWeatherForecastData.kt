package com.bittelasia.holidayinn.domain.model.weather.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_weather_forecast")
data class GetWeeklyWeatherForecastData(
    val date: String?,
    val description: String?,
    val dew_point: String?,
    val humidity: String?,
    val icon: String?,
    @PrimaryKey
    val id: String,
    val pressure: String?,
    val sunrise: String?,
    val sunset: String?,
    val temp_day: String?,
    val temp_eve: String?,
    val temp_max: String?,
    val temp_min: String?,
    val temp_morn: String?,
    val temp_night: String?,
    val wind_speed: String?
)