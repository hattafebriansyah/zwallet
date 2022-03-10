package com.hatta.zwallet.ui.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatta.zwallet.R
import com.hatta.zwallet.adapter.TransactionAdapter
import com.hatta.zwallet.data.Transaction
import com.hatta.zwallet.databinding.FragmentHomeBinding
import android.content.SharedPreferences
import android.widget.Toast
import androidx.navigation.Navigation
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.UserDetail
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.ui.SplashScreen
import com.hatta.zwallet.utils.KEY_LOGGED_IN
import com.hatta.zwallet.utils.PREFS_NAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs : SharedPreferences

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

        this.transactionAdapter = TransactionAdapter(transactionData)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerTransaction.layoutManager = layoutManager
        binding.recyclerTransaction.adapter = transactionAdapter
        prepareData()
        getProfile()
        getBalance()

        binding.cardBalance.imageUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }
    }

    private fun getProfile() {
        NetworkConfig(requireContext()).getService().getUserDetail()
            .enqueue(object : Callback<APIResponse<UserDetail>> {
                override fun onResponse(
                    call: Call<APIResponse<UserDetail>>,
                    response: Response<APIResponse<UserDetail>>
                ) {
                    val res = response.body()?.data
                    //binding.cardBalance.nameAccount.text = response.body()?.data?.firstname.toString()
                    binding.cardBalance.nameAccount.text = "${res?.firstname} "
                }

                override fun onFailure(call: Call<APIResponse<UserDetail>>, t: Throwable) {
                    Toast.makeText(requireContext(), "failed", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getBalance() {
        NetworkConfig(requireContext()).getService().getBalance()
            .enqueue(object : Callback<APIResponse<List<Balance>>> {
                override fun onResponse(
                    call: Call<APIResponse<List<Balance>>>,
                    response: Response<APIResponse<List<Balance>>>
                ) {
                    binding.cardBalance.balance.text = response.body()?.data?.get(0)?.balance.toString()
                    binding.cardBalance.phoneAccount.text = response.body()?.data?.get(0)?.phone.toString()
                }

                override fun onFailure(call: Call<APIResponse<List<Balance>>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
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

        this.transactionAdapter.notifyDataSetChanged()
    }
}




