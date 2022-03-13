package com.hatta.zwallet.ui.main.home

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
import android.os.Handler
import android.widget.Toast
import androidx.navigation.Navigation
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.UserDetail
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.ui.auth.login.LoginViewModel
import com.hatta.zwallet.ui.main.MainActivity
import com.hatta.zwallet.ui.viewModelsFactory
import com.hatta.zwallet.utils.*
import com.hatta.zwallet.utils.Helper.formatPrice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class HomeFragment : Fragment() {
    private val transactionData = mutableListOf<Transaction>()
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var prefs : SharedPreferences
    private val viewModel : HomeViewModel by viewModelsFactory { HomeViewModel(requireActivity().application)  }

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
        prepareData()
       // getProfile()

        binding.cardBalance.imageUser.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }

        binding.cardBalance.textButtonTopUp.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.homeActionTopUp)
        }
    }

    private fun getProfile() {
        NetworkConfig(requireContext()).buildApi().getUserDetail()
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

    private fun prepareData(){
        this.transactionAdapter = TransactionAdapter(listOf())
        binding.recyclerTransaction.apply {
            val layoutManager  = LinearLayoutManager(context)
            adapter = transactionAdapter
        }

        viewModel.getBalance().observe(viewLifecycleOwner){
            if (it.status == HttpsURLConnection.HTTP_OK){
                this.transactionAdapter.apply {
                    binding.apply {
                        cardBalance.nameAccount.text = it.data?.get(0)?.name
                        cardBalance.balance.formatPrice(it.data?.get(0)?.balance.toString())
                        cardBalance.phoneAccount.text = it.data?.get(0)?.phone
                    }
                }
            }
            else {
                Toast.makeText(context, "Authentication failed: Wrong email/password", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.getInvoice().observe(viewLifecycleOwner){
            if (it.status == HttpsURLConnection.HTTP_OK){
               this.transactionAdapter.apply {
               addData(it.data!!)
                   notifyDataSetChanged()
               }
            }
            else {
                Toast.makeText(context, "Authentication failed: Wrong email/password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}




