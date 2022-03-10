package com.hatta.zwallet.ui.auth

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentLoginBinding
import com.hatta.zwallet.databinding.FragmentRegisterBinding
import com.hatta.zwallet.ui.main.MainActivity
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.LoginRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.ui.main.HomeFragment
import com.hatta.zwallet.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var prefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = activity?.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)!!

        binding.btnLogin.setOnClickListener {
            if (binding.inputEmail.text.isNullOrEmpty()|| binding.inputPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Email / Password is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val loginRequest = LoginRequest(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            NetworkConfig(context).getService().login(loginRequest)
                .enqueue(object : Callback<APIResponse<User>>{
                    override fun onResponse(
                        call: Call<APIResponse<User>>,
                        response: Response<APIResponse<User>>
                    ) {

                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "Authentication Failed : Wrong Email / Password", Toast.LENGTH_SHORT).show()
                        } else {

                            val res = response.body()!!.data

                            with (prefs.edit()){
                                putBoolean(KEY_LOGGED_IN, true)
                                putString(KEY_USER_EMAIL, res.email)
                                putString(KEY_USER_TOKEN, res.token)
                                putString(KEY_USER_REFRESH_TOKEN, res.refreshToken)
                                apply()
                            }

                            Handler().postDelayed({
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }, 2000)
                        }
                    }

                    override fun onFailure(call: Call<APIResponse<User>>, t: Throwable) {
                        Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                })
        }

        binding.textGotoLoginLink.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.loginActionRegister)
        }

        binding.textForgotPassword.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.loginActionForgotPassword)
        }
    }
    }


