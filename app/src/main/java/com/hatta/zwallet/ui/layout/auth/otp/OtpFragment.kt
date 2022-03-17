package com.hatta.zwallet.ui.layout.auth.otp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import com.hatta.zwallet.R
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.databinding.FragmentOtpBinding
import com.hatta.zwallet.utils.PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var prefs : SharedPreferences
    var otp  = mutableListOf<EditText>()
    private lateinit var transactionAdapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        initEditText()

    }
    fun initEditText(){
        otp.add(0,binding.otp1)
        otp.add(1,binding.otp2)
        otp.add(2,binding.otp3)
        otp.add(3,binding.otp4)
        otp.add(4,binding.otp5)
        otp.add(5,binding.otp6)
        otpHandler()

    }
    fun otpHandler(){
        for (i in 0..5) { //Its designed for 6 digit PIN
            otp.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    otp.get(i).setBackgroundResource(R.drawable.background_change_pin_after)

                }
                override fun afterTextChanged(s: Editable) {
                    if (i == 5 && !otp.get(i).getText().toString().isEmpty()) {
                        otp.get(i).clearFocus()

                        //Clears focus when you have entered the last digit of the OTP.
                    } else if (!otp.get(i).getText().toString().isEmpty()) {
                        otp.get(i + 1).requestFocus() //focuses on the next edittext after a digit is entered.
                    }
                }
            })
            otp.get(i).setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action !== KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                }
                if (keyCode == KeyEvent.KEYCODE_DEL && otp.get(i).getText().toString().isEmpty() && i != 0) {
                    //this condition is to handel the delete input by users.
                    otp.get(i - 1).setText("") //Deletes the digit of OTP
                    otp.get(i - 1).requestFocus()
                    //and sets the focus on previous digit
                }
                false
            })

        }
        binding.btnConfirm.setOnClickListener{
            setOtp()
        }

    }
    fun getOtp():String{
        return otp[0].text.toString()+otp[1].text.toString()+otp[2].text.toString()+otp[3].text.toString()+otp[4].text.toString()+otp[5].text.toString()
    }

    fun setOtp(){
        Toast.makeText(activity, getOtp(), Toast.LENGTH_LONG).show()
    }
}