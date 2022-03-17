package com.hatta.zwallet.ui.layout.auth.login

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentLoginBinding
import com.hatta.zwallet.ui.layout.main.MainActivity
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.hatta.zwallet.utils.*
import com.hatta.zwallet.ui.widget.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        preferences = context?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isNullOrEmpty() || binding.inputPassword.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Email / Password is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val response = viewModel.login(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            response.observe(viewLifecycleOwner){
               when(it.state){
                   State.LOADING ->{
                        loadingDialog.start("Prosessing your request")
                   }
                   State.SUCCESS ->{
                       loadingDialog.stop()
                       if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                           with (preferences.edit()) {
                               putBoolean(KEY_LOGGED_IN, true)
                               putString(KEY_USER_EMAIL, it.resource.data?.email)
                               putString(KEY_USER_TOKEN, it.resource.data?.token)
                               putString(KEY_USER_REFRESH_TOKEN, it.resource.data?.refreshToken)
                               apply()
                           }
                           if(it.resource.data?.hasPin!!) {
                               Handler().postDelayed({
                                   val intent = Intent(activity, MainActivity::class.java)
                                   startActivity(intent)
                                   activity?.finish()
                               }, 1000)
                           }
                           else{
                               Navigation.findNavController(view)
                                   .navigate(R.id.loginActionCreatePin)
                           }
                       }
                       else {
                           Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                               .show()
                       }
                   }
                   State.ERROR ->{
                       loadingDialog.stop()
                       Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                           .show()
                   }
               }
            }


        }
        binding.textGotoLoginLink.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginActionRegister)
        }

        binding.textForgotPassword.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.loginActionForgotPassword)
        }
    }
}


