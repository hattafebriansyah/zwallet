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
import com.hatta.zwallet.databinding.FragmentChangePasswordBinding
import com.hatta.zwallet.databinding.FragmentProfileBinding
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.ChangePasswordRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.widget.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var loadingDialog: LoadingDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.changePasswordActionProfile)
        }

        binding.btnChangePassword.setOnClickListener {
            if (binding.currentPassword.text.isNullOrEmpty() || binding.newPassword.text.isNullOrEmpty() || binding.repeatPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Current Password or New Password is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.newPassword.text.isNullOrEmpty() != binding.repeatPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val changePasswordRequest = ChangePasswordRequest(
                binding.newPassword.text.toString(),
                binding.currentPassword.text.toString(),
            )
            NetworkConfig(context).buildApi().changePassword(changePasswordRequest)
                .enqueue(object : Callback<APIResponse<User>> {
                    override fun onResponse(
                        call: Call<APIResponse<User>>,
                        response: Response<APIResponse<User>>
                    ) {
                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            val res = response.body()!!.message
                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view).popBackStack()
                        }
                    }
                    override fun onFailure(call: Call<APIResponse<User>>, t: Throwable) {
                        Toast.makeText(context, "Failed to change password", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }


}