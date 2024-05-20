package com.bittelasia.holidayinn.domain.model.broadcast

data class BroadCastData(
    val message: String,
    val time: Int,
    val type: String,
    val url: String?
)