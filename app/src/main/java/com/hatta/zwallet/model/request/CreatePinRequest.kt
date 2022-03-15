package com.hatta.zwallet.model.request


import com.google.gson.annotations.SerializedName

data class CreatePinRequest(
    @SerializedName("PIN")
    val pIN: String
)