package com.hatta.zwallet.ui.layout.main.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.databinding.FragmentProfileBinding
import com.hatta.zwallet.ui.layout.SplashScreen
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import com.hatta.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel : ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        prepareData()

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }
        binding.btnPersonalInformation.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.profileActionPersonalInformation)
        }
        binding.btnChangePassword.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.profileActionChangePassword)
        }
        binding.btnChangePin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.profileActionChangePin)
        }

        binding.btnLogout.setOnClickListener{
            AlertDialog.Builder(context)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes"){_,_ ->
                    with(prefs.edit()){
                        putBoolean(com.hatta.zwallet.utils.KEY_LOGGED_IN,false)
                        apply()
                    }
                    val intent = Intent (activity, SplashScreen::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("Cancel"){_,_ ->
                    return@setNegativeButton
                }
                .show()
        }
    }


    private fun prepareData() {
        this.transactionAdapter = TransactionAdapter(listOf())

        viewModel.getBalance().observe(viewLifecycleOwner) {
            when(it.state) {
                State.LOADING -> {
                    loadingDialog.start("Go to Profile")
                }
                State.SUCCESS -> {
                    if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                        this.transactionAdapter.apply {
                            binding.apply {
                                nameAccount.text = it.resource?.data?.get(0)?.name
                                phoneAccount.text = it.resource?.data?.get(0)?.phone
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