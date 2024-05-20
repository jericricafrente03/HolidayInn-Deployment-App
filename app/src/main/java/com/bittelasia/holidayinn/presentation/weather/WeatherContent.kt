package com.bittelasia.holidayinn.presentation.weather

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel

@Composable
fun WeatherContent(
    viewModel: ApplicationViewModel,
    navHostController: NavHostController
) {
    WeatherData(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        navHostController = navHostController
    )
}