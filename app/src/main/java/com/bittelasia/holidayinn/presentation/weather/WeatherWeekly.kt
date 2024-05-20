package com.bittelasia.holidayinn.presentation.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.presentation.components.CustomBackButton
import com.bittelasia.holidayinn.presentation.components.Theme
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun WeatherWeekly(
    modifier: Modifier = Modifier,
    viewModel: ApplicationViewModel,
    navHostController: NavHostController
) {
    val weatherData by viewModel.selectedListWeather.collectAsState()
    val selectedWeatherTop by viewModel.themeWeatherBottom.collectAsState()

    Theme(zone = selectedWeatherTop, modifier = modifier) {
        Column {
            LazyRow(
                modifier = Modifier
                    .weight(.7f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                weatherData?.let { data ->
                    items(data.size) { list ->
                        val item = data[list]
                        DailyWeatherItem(
                            weatherUpdate = item,
                            zone = selectedWeatherTop
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxWidth(), contentAlignment = Alignment.CenterStart,
            ) {
                CustomBackButton(navHostController = navHostController)
            }
        }
    }
}