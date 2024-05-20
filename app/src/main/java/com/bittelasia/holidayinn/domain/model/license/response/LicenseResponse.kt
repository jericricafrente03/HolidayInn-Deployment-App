package com.bittelasia.holidayinn.domain.model.license.response

import com.google.gson.annotations.SerializedName


data class LicenseResponse(
    @SerializedName("end_date")
    val endDate: String = "",
    @SerializedName("result")
    val result: String = "",
    @SerializedName("remaining_days")
    val remaining_days: String = ""
)