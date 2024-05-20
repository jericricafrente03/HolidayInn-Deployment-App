package com.bittelasia.holidayinn.domain.model.post_stat

import com.google.gson.annotations.SerializedName

data class PostStatistics(
    @SerializedName("device_id")
    val device_id: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("item_id")
    val item_id: Int,
    @SerializedName("item_type")
    val item_type: String,
    @SerializedName("start")
    val start: String
)