package com.hatta.zwallet.ui.layout.main.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatta.zwallet.R
import com.hatta.zwallet.data.Transaction
import com.hatta.zwallet.databinding.FragmentHomeBinding
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.ui.widget.LoadingDialog
import com.hatta.zwallet.utils.Helper.formatPrice
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        prepareData()

        binding.cardBalance.imageUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }

        binding.cardBalance.textButtonTopUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionTopUp)
        }
        binding.cardBalance.textButtonTransfer.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionFindReceiver)
        }
    }


    private fun prepareData(){
        this.transactionAdapter = TransactionAdapter(listOf())
        binding.recyclerTransaction.apply {
            val layoutManager  = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner){
            when(it.state){
                State.LOADING ->{
                    loadingDialog.start("Fetching your personal data")
                }

                State.SUCCESS ->{
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                        this.transactionAdapter.apply {
                            binding.apply {
                                cardBalance.nameAccount.text = it.resource.data?.get(0)?.name
                                cardBalance.balance.formatPrice(it.resource.data?.get(0)?.balance.toString())
                                cardBalance.phoneAccount.text = it.resource.data?.get(0)?.phone
                            }
                        }
                    }
                    else {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    loadingDialog.dismiss()
                }
                State.ERROR -> {
                    loadingDialog.stop()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        viewModel.getInvoice().observe(viewLifecycleOwner){
            when(it.state){
                State.LOADING ->{
                    binding.apply {
                    loadingIndicator.visibility = View.VISIBLE
                    recyclerTransaction.visibility = View.GONE
                }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransaction.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.transactionAdapter.apply {
                            addData(it.resource.data!!)
                            notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                    loadingDialog.dismiss()


                }
                State.ERROR  ->{
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransaction.visibility = View.VISIBLE
                    }
                }
            }


        }
    }
}




