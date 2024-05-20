package com.bittelasia.holidayinn.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bittelasia.holidayinn.domain.model.theme.item.Zone
import com.bittelasia.holidayinn.domain.model.weather.item.GetWeeklyWeatherForecastData
import com.bittelasia.holidayinn.presentation.components.TextFormat
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.components.capitalizeWord
import com.bittelasia.holidayinn.presentation.components.getDrawableResource


@Composable
fun WeatherTodayContent(
    weather: GetWeeklyWeatherForecastData?,
    zone: Zone?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Theme(zone = zone, modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                TextFormat(
                    value = weather?.temp_min?.plus(" °C") ?: "Not available",
                    color = zone?.text_color,
                    size = 80.sp,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                )
                TextFormat(
                    value = capitalizeWord(weather?.description ?: "Not available"),
                    color = zone?.text_color,
                    size = 70.sp,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                )
            }
            Spacer(modifier = Modifier.width(50.dp))
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(getDrawableResource(weather?.icon))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp),
                contentScale = ContentScale.Inside
            )
        }
    }
}



