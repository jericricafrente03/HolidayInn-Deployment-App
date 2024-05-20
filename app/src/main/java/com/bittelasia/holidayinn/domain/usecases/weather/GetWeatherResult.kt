package com.bittelasia.holidayinn.domain.usecases.weather

import com.bittelasia.holidayinn.data.local.DataState
import com.bittelasia.holidayinn.data.repository.MeshListApiImpl
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecastData
import kotlinx.coroutines.flow.Flow

class GetWeatherResult(
    private val repository: MeshListApiImpl
) {
    operator fun invoke(): Flow<DataState<List<GetWeeklyWeatherForecastData>>> {
        return repository.weatherResult()
    }
}