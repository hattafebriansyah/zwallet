package com.hatta.zwallet.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatta.zwallet.R
import com.hatta.zwallet.adapter.TransactionAdapter
import com.hatta.zwallet.data.Transaction
import com.hatta.zwallet.databinding.ActivityMainBinding
import com.hatta.zwallet.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.transactionAdapter = TransactionAdapter(transactionData)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()

        binding.cardBalance.imageUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }
    }
    private fun prepareData(){
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user)!!,
            transactionName = "Hatta Febriansyah",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user2)!!,
            transactionName = "Efrinaldi Al-Zuhri",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user3)!!,
            transactionName = "M Aftabuddin Arsyad",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user)!!,
            transactionName = "Hatta Febriansyah",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user2)!!,
            transactionName = "Efrinaldi Al-Zuhri",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionData.add(Transaction(
            transactionImage = activity?.getDrawable(R.drawable.user3)!!,
            transactionName = "M Aftabuddin Arsyad",
            transactionType = "Transfer",
            transactionNominal = 100000.00
        ))
        this.transactionAdapter.notifyDataSetChanged()
    }



}