package com.hatta.zwallet.model


import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("username")
    val username: String?
)