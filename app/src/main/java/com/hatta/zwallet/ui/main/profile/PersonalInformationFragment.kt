package com.hatta.zwallet.ui.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.adapter.TransactionAdapter
import com.hatta.zwallet.databinding.FragmentPersonalInformationBinding
import com.hatta.zwallet.ui.viewModelsFactory
import com.hatta.zwallet.utils.PREFS_NAME
import javax.net.ssl.HttpsURLConnection


class PersonalInformationFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInformationBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var transactionAdapter: TransactionAdapter
    private val viewModel : ProfileViewModel by viewModelsFactory { ProfileViewModel(requireActivity().application)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPersonalInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        prepareData()

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.personalInformationActionProfile)
        }
    }

    private fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.status == HttpsURLConnection.HTTP_OK) {
                this.transactionAdapter.apply {
                    binding.apply {
                        textFirstName.text = it.data?.get(0)?.name
                        textLastName.text = it.data?.get(0)?.phone
                        textVerifiedEmail.text = it.data?.get(0)?.phone
                        textPhone.text = it.data?.get(0)?.phone
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "Gagal memuat proses",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }
}