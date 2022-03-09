package com.hatta.zwallet.data

import android.graphics.drawable.Drawable

//hanya untuk menyimpan data
data class Transaction(
    val transactionImage: Drawable,
    val transactionName: String,
    val transactionType : String,
    val transactionNominal: Double,
)

