package com.bittelasia.holidayinn.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bittelasia.holidayinn.presentation.home.DashboardScreen
import com.bittelasia.holidayinn.presentation.register.presenter.LoginScreenContent
import com.bittelasia.holidayinn.presentation.register.presenter.PingScreen
import com.bittelasia.holidayinn.presentation.splashscreen.AnimatedSplashScreen

@Composable
fun AppSwitcher(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Screens.SplashScreen.title) {
        composable(route = Screens.SplashScreen.title) {
            AnimatedSplashScreen(navHostController = navHostController)
        }
        composable(route = Screens.Login.title) {
            LoginScreenContent(
                onNavigateToHomeScreen = {
                    navHostController.navigateSingleTopTo(Screens.Home.title)
                },
                onNavigateToPing = {
                    navHostController.navigateSingleTopTo(Screens.PingServer.title)
                }
            )
        }
        composable(route = Screens.Home.title) {
            DashboardScreen()
        }
        composable(route = Screens.PingServer.title){
            PingScreen()
        }
    }
}


fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }