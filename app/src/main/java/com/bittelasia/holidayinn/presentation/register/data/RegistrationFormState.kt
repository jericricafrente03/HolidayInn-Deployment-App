package com.bittelasia.holidayinn.presentation.register.data

data class RegistrationFormState(
    val ip: String ="",
    val ipErr: String? =null,
    val port: String = "",
    val portErr: String? =null,
    val room: String ="",
    val roomErr: String? =null
)