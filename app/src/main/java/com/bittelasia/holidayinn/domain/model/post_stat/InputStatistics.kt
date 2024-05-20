package com.bittelasia.holidayinn.domain.model.post_stat

import com.google.gson.annotations.SerializedName

class InputStatistics(
    @SerializedName("list")
    val list: List<PostStatistics>
)