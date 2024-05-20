package com.bittelasia.holidayinn.domain.model.post_stat

import com.google.gson.annotations.SerializedName

data class StatResponse(
    @SerializedName("count")
    val count: String
)