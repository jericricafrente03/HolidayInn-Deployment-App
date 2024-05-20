package com.bittelasia.holidayinn.domain.model.result

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("reasons")
    val reasons: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("room_number")
    val roomNumber: String
)