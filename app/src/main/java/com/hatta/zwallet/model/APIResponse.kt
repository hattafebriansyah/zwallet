package com.hatta.zwallet.model

data class APIResponse<T>(
    var status: Int,
    var message: String,
    var data: T?
)
