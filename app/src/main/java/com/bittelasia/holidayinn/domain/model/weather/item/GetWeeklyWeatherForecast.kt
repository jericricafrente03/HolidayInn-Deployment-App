package com.bittelasia.holidayinn.domain.model.weather.item

data class GetWeeklyWeatherForecast(
    val `data`: List<GetWeeklyWeatherForecastData>
)