package com.hatta.zwallet.model.request

data class TransferRequest(
    var receiver: String,
    var amount: Int,
    var notes: String?
)
