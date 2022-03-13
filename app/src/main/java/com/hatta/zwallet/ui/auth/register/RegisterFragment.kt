package com.hatta.zwallet.ui.auth.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentRegisterBinding
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.RegisterRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.ui.auth.AuthActivity
import com.hatta.zwallet.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var prefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!


        binding.btnSignUp.setOnClickListener {
            if (binding.inputUsername.text.isNullOrEmpty()|| binding.inputEmail.text.isNullOrEmpty()|| binding.inputPassword.text.isNullOrEmpty()){
                Toast.makeText(activity, "Username / Email / Password cannot be Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val registerRequest = RegisterRequest(
                binding.inputUsername.text.toString(),
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )

            NetworkConfig(context).buildApi().register(registerRequest)
                .enqueue(object : Callback<APIResponse<User>> {
                    override fun onResponse(
                        call: Call<APIResponse<User>>,
                        response: Response<APIResponse<User>>
                    ) {

                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
                            Toast.makeText(context, "Register Failed ! Please Try Again", Toast.LENGTH_SHORT).show()
                        } else {
                            val res = response.body()!!.message
                            Toast.makeText(context, res, Toast.LENGTH_SHORT).show()
                            Handler().postDelayed({
                                val intent = Intent(activity, AuthActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }, 1000)
                        }
                    }
                    override fun onFailure(call: Call<APIResponse<User>>, t: Throwable) {
                        Toast.makeText(context,t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                })
        }




        binding.textGotoLoginLink.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.registerActionLogin)
        }
    }
}