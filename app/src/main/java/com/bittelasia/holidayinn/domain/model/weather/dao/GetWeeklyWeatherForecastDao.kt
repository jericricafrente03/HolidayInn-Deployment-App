package com.bittelasia.holidayinn.domain.model.weather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecastData

@Dao
interface GetWeeklyWeatherForecastDao {
    @Query("SELECT * FROM weekly_weather_forecast")
    fun getAllWeeklyWeather(): List<GetWeeklyWeatherForecastData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyWeather(hourlyWeather: List<GetWeeklyWeatherForecastData>)

    @Query("DELETE FROM weekly_weather_forecast")
    suspend fun deleteAllWeeklyWeather()
}