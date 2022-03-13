package com.hatta.zwallet.ui.auth.login

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
import com.hatta.zwallet.ui.main.MainActivity
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hatta.zwallet.ui.viewModelsFactory
import com.hatta.zwallet.utils.*
import javax.net.ssl.HttpsURLConnection

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModelsFactory {LoginViewModel(requireActivity().application)  }
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        preferences = context?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)!!
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
                if (it.status == HttpsURLConnection.HTTP_OK){
                    with (preferences.edit()) {
                        putBoolean(KEY_LOGGED_IN, true)
                        putString(KEY_USER_EMAIL, it.data?.email)
                        putString(KEY_USER_TOKEN, it.data?.token)
                        putString(KEY_USER_REFRESH_TOKEN, it.data?.refreshToken)
                        apply()
                    }

                    Handler().postDelayed({
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }, 2000)
                }
                else {
                    Toast.makeText(context, "Authentication failed: Wrong email/password", Toast.LENGTH_SHORT)
                        .show()
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
}


