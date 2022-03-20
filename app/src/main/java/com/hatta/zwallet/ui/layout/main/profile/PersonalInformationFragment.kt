package com.hatta.zwallet.ui.layout.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.databinding.FragmentPersonalInformationBinding
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import com.hatta.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {

    private lateinit var binding: FragmentPersonalInformationBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel : PersonalInformationViewModel by activityViewModels()

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
        loadingDialog = LoadingDialog(requireActivity())

        prepareData()

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        binding.textManage.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_personalInformationFragment_to_managePhoneFragment)
        }
    }

    private fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())

        viewModel.getUserDetail().observe(viewLifecycleOwner) {
            when(it.state) {
                State.LOADING -> {
                    loadingDialog.start("Prosessing your request")
                }

                State.SUCCESS -> {
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.transactionAdapter.apply {
                            binding.apply {
                                textFirstName.text = it.resource?.data?.firstname
                                textLastName.text = it.resource?.data?.lastname
                                textVerifiedEmail.text = it.resource?.data?.email
                                textPhone.text = it.resource?.data?.phone
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            it.message,
                            Toast.LENGTH_SHORT
                        )
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
    }
}