package com.bittelasia.holidayinn.domain.model.broadcast

data class Broadcast(
    val action: String,
    val `class`: String,
    val `data`: BroadCastData,
    val room: String
)