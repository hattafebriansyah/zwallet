package com.hatta.zwallet.model


import com.google.gson.annotations.SerializedName

data class ContactUser(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone")
    val phone: String?
)