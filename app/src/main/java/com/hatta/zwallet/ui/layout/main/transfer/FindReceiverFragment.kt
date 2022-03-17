package com.hatta.zwallet.ui.layout.main.transfer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.hatta.zwallet.ui.adapter.ContactAdapter
import com.hatta.zwallet.data.Contact
import com.hatta.zwallet.databinding.FragmentFindReceiverBinding
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import com.hatta.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class FindReceiverFragment : Fragment() {
    private val transactionData = mutableListOf<Contact>()
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var binding: FragmentFindReceiverBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel : FindReceiverViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindReceiverBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        prepareData()

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }


    }

    private fun prepareData(){
        this.contactAdapter = ContactAdapter(listOf())
        binding.recyclerTransactionContact.apply {
            val layoutManager  = LinearLayoutManager(context)
            this.layoutManager = layoutManager
            adapter = contactAdapter
        }

        viewModel.getContactUser().observe(viewLifecycleOwner){
            when(it.state){
                State.LOADING ->{
                    binding.apply {
                        loadingIndicator.visibility = View.VISIBLE
                        recyclerTransactionContact.visibility = View.GONE
                    }
                }
                State.SUCCESS -> {
                    binding.apply {
                        loadingIndicator.visibility = View.GONE
                        recyclerTransactionContact.visibility = View.VISIBLE
                    }
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.contactAdapter.apply {
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
                        recyclerTransactionContact.visibility = View.VISIBLE
                    }
                }
            }


        }
    }
}