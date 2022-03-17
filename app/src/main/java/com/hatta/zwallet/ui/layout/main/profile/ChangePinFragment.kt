package com.hatta.zwallet.ui.layout.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.databinding.FragmentChangePinBinding
import com.hatta.zwallet.utils.PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePinFragment : Fragment() {
    private lateinit var binding: FragmentChangePinBinding
    private lateinit var prefs : SharedPreferences
    var pin  = mutableListOf<EditText>()
    private lateinit var transactionAdapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        initEditText()
        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.changePinActionProfile)
        }
    }
    fun initEditText(){
        pin.add(0,binding.pin1)
        pin.add(1,binding.pin2)
        pin.add(2,binding.pin3)
        pin.add(3,binding.pin4)
        pin.add(4,binding.pin5)
        pin.add(5,binding.pin6)
        pinHandler()
    }
    fun pinHandler(){
        for (i in 0..5) { //Its designed for 6 digit PIN
            pin.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    pin.get(i).setBackgroundResource(R.drawable.background_change_pin_after)

                }
                override fun afterTextChanged(s: Editable) {
                    if (i == 5 && !pin.get(i).getText().toString().isEmpty()) {
                        pin.get(i).clearFocus()

                        //Clears focus when you have entered the last digit of the OTP.
                    } else if (!pin.get(i).getText().toString().isEmpty()) {
                        pin.get(i + 1).requestFocus() //focuses on the next edittext after a digit is entered.
                    }
                }
            })
            pin.get(i).setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action !== KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                }
                if (keyCode == KeyEvent.KEYCODE_DEL && pin.get(i).getText().toString().isEmpty() && i != 0) {
                    //this condition is to handel the delete input by users.
                    pin.get(i - 1).setText("") //Deletes the digit of OTP
                    pin.get(i - 1).requestFocus()
                    //and sets the focus on previous digit
                }
                false
            })

        }
        binding.btnChangePin.setOnClickListener{
            setPin()
        }

    }
    fun getPin():String{
        return pin[0].text.toString()+pin[1].text.toString()+pin[2].text.toString()+pin[3].text.toString()+pin[4].text.toString()+pin[5].text.toString()
    }

    fun setPin(){
            Toast.makeText(activity, getPin(), Toast.LENGTH_LONG).show()
        }
    }
