package com.bittelasia.holidayinn.navigation


sealed class Screens(val title: String){
    object SplashScreen : Screens("splashScreen")
    object Login : Screens("login")
    object Home : Screens("home")
    object PingServer : Screens("ping")
}