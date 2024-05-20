package com.bittelasia.holidayinn.presentation.register.data

sealed class RegistrationFormEvent {
    data class IpChanged(val ip: String) : RegistrationFormEvent()
    data class PortChange(val port: String) : RegistrationFormEvent()
    data class RoomChanged(val room: String) : RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}