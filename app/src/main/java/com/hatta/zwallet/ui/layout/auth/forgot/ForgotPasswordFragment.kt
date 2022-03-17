package com.hatta.zwallet.ui.layout.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

        private lateinit var binding: FragmentForgotPasswordBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

            binding.btnConfirm.setOnClickListener{
                Navigation.findNavController(view).navigate(R.id.forgotPasswordActionConfirmPassword)
            }
        }
    }


