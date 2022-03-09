package com.hatta.zwallet.ui.main

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hatta.zwallet.R
import com.hatta.zwallet.adapter.TransactionAdapter
import com.hatta.zwallet.data.Transaction
import com.hatta.zwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.transactionAdapter = TransactionAdapter(transactionData)

        val layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()
    }

    private fun prepareData(){
       this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user)!!,
            transactionName = "Hatta Febriansyah",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user2)!!,
            transactionName = "Efrinaldi Al-Zuhri",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user3)!!,
            transactionName = "M Aftabuddin Arsyad",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user)!!,
            transactionName = "Hatta Febriansyah",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user2)!!,
            transactionName = "Efrinaldi Al-Zuhri",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = getDrawable(R.drawable.user3)!!,
            transactionName = "M Aftabuddin Arsyad",
            transactionType = "Transfer",
            transactionNominal = +100000.00
        ))
        this.transactionAdapter.notifyDataSetChanged()
    }


}