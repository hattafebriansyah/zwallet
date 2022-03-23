package com.hatta.zwallet.ui.layout.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentChangePinBinding
import com.hatta.zwallet.ui.widget.LoadingDialog
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ChangePinFragment : Fragment() {
    private lateinit var binding: FragmentChangePinBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: ProfileViewModel by activityViewModels()
    var pin  = mutableListOf<EditText>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangePinBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        initEditText()

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnChangePin.setOnClickListener {
            val pinNumber = binding.pin1.text.toString()+
                    binding.pin2.text.toString()+
                    binding.pin3.text.toString()+
                    binding.pin4.text.toString()+
                    binding.pin5.text.toString()+
                    binding.pin6.text.toString()

            viewModel.checkPin(pinNumber.toInt()).observe(viewLifecycleOwner){
                when(it.state){
                    State.LOADING -> {
                        loadingDialog.start("Checking Your PIN")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                            Navigation.findNavController(view).navigate(R.id.action_changePinFragment_to_changePinNewFragment)
                            loadingDialog.stop()
                        } else {
                            Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    State.ERROR -> {
                        loadingDialog.stop()
                        Toast.makeText(context, "PIN not Match", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
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
                    pin.get(i).setBackgroundResource(R.drawable.background_change_pin)
                    //and sets the focus on previous digit
                }
                false
            })
        }
    }
}
