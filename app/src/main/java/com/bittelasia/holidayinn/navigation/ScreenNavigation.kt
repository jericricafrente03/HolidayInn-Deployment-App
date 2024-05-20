package com.bittelasia.holidayinn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bittelasia.holidayinn.presentation.home.main.MeshBackground
import com.bittelasia.holidayinn.presentation.home.ApplicationViewModel
import com.bittelasia.holidayinn.presentation.home.topbar.HomePageContent
import com.bittelasia.holidayinn.presentation.hotelinfo.InfoContent
import com.bittelasia.holidayinn.presentation.message.MessageContent
import com.bittelasia.holidayinn.presentation.weather.WeatherContent

@Composable
fun ScreenNavigation(
    navController: NavHostController,
    viewModel: ApplicationViewModel
) {
    MeshBackground(navController = navController, viewModel = viewModel) {
        NavHost(
            navController = navController,
            startDestination = NestedScreens.Home.title
        ) {
            composable(NestedScreens.Home.title) {
                HomePageContent(viewModel = viewModel)
            }
            composable(NestedScreens.Weather.title) {
                WeatherContent(viewModel = viewModel, navHostController = navController)
            }
            composable(NestedScreens.Message.title) {
                MessageContent(viewModel = viewModel, navHostController = navController)
            }
            composable(NestedScreens.Facilities.title) {
                InfoContent(viewModel = viewModel, navHostController = navController)
            }
        }
    }
}


