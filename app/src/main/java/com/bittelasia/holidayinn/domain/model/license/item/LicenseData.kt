package com.bittelasia.holidayinn.domain.model.license.item

import com.google.gson.annotations.SerializedName

data class LicenseData(
    @SerializedName("data")
    val key: LicenseKey
)