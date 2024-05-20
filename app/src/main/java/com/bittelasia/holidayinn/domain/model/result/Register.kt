package com.bittelasia.holidayinn.domain.model.result

import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("data")
    var data: Result,
    @SerializedName("jid_pass")
    val jid_pass: String,
    @SerializedName("username")
    val jid_user: String,
    @SerializedName("device_id")
    val device_id: String
)

class InvalidNoteException(message: String) : Exception(message)
