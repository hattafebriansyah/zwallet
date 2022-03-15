package com.hatta.zwallet.ui.auth.createpin

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
import com.hatta.zwallet.databinding.FragmentCreatePinSuccessBinding
import com.hatta.zwallet.utils.PREFS_NAME


class CreatePinSuccessFragment : Fragment() {
    private lateinit var binding: FragmentCreatePinSuccessBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePinSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        prefs = activity?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        binding.btnConfirm.setOnClickListener() {
            Toast.makeText(context, "Please Wait", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.createPinSuccessActionLogin)
        }
    }
}