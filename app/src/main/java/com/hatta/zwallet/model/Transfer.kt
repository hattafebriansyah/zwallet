package com.hatta.zwallet.model


import com.google.gson.annotations.SerializedName

data class Transfer(
    @SerializedName("amount")
    val amount: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("notes")
    val notes: String?,
    @SerializedName("receiver")
    val `receiver`: Int?,
    @SerializedName("sender")
    val sender: Int?,
    @SerializedName("type")
    val type: Int?
)