package com.hatta.zwallet.model.request


import com.google.gson.annotations.SerializedName

data class ManagePhoneRequest(
    @SerializedName("phone")
    val phone: String?
)