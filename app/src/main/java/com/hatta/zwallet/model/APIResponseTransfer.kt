package com.hatta.zwallet.model


import com.google.gson.annotations.SerializedName

data class APIResponseTransfer<T>(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("receiver")
    val `receiver`: String?
)