package com.bittelasia.holidayinn.domain.model.stb

data class StbRegistration(
    val ip: String,
    val port: String,
    val room: String,
    val apiKey: String,
    val mac: String
)
