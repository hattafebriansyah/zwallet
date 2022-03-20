package com.hatta.zwallet.model

data class APIResponseTransfer<T>(
    var status: Int,
    var message: String,
    var details: T?
)