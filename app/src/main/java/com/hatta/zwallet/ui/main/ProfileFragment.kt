package com.hatta.zwallet.ui.main

import android.app.AlertDialog
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
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentLoginBinding
import com.hatta.zwallet.databinding.FragmentProfileBinding
import com.hatta.zwallet.ui.SplashScreen
import com.hatta.zwallet.utils.KEY_LOGGED_IN
import com.hatta.zwallet.utils.PREFS_NAME


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs : SharedPreferences

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

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.profileActionHome)
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
}