package com.bittelasia.holidayinn.navigation

sealed class NestedScreens(val title: String) {
    object Home : NestedScreens("home")
    object Message : NestedScreens("Messaging")
    object Weather : NestedScreens("Weather")
    object Facilities : NestedScreens("Hotel Info")
}