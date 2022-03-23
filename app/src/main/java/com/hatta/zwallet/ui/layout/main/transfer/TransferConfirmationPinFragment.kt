package com.hatta.zwallet.ui.layout.main.transfer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentChangePinBinding
import com.hatta.zwallet.databinding.FragmentTransferConfirmationPinBinding
import com.hatta.zwallet.model.request.TransferRequest
import com.hatta.zwallet.ui.adapter.TransactionAdapter
import com.hatta.zwallet.ui.widget.LoadingDialog
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferConfirmationPinFragment : Fragment() {
    private lateinit var binding: FragmentTransferConfirmationPinBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var prefs : SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: TransferViewModel by activityViewModels()
    var pin  = mutableListOf<EditText>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferConfirmationPinBinding.inflate(layoutInflater)
        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editPin()
        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).popBackStack()
        }

        binding.btnTransfer.setOnClickListener{
           transfer(it)
        }
    }
    fun transfer(view: View){

        var receiver : String?=null
        var amount : String?=null
        var notes : String?=null
        var request: TransferRequest ?=null

        val pinNumber = binding.pin1.text.toString()+
                binding.pin2.text.toString()+
                binding.pin3.text.toString()+
                binding.pin4.text.toString()+
                binding.pin5.text.toString()+
                binding.pin6.text.toString()


        viewModel.getTransferParameter().observe(viewLifecycleOwner){
            request = it
            binding.apply {
                receiver = "${it.receiver}"
                amount = "${it.amount}"
                notes = "${it.notes}"
            }
        }

        viewModel.transfer(request!!, pinNumber).observe(viewLifecycleOwner) {
            when(it.state){
                State.LOADING ->{
                    loadingDialog.start("Processing Your Transfer")
                }
                State.SUCCESS ->{
                    loadingDialog.start("Transfer Success")
                    loadingDialog.stop()
                    Navigation.findNavController(view).navigate(R.id.action_transferConfirmationPinFragment2_to_transferSuccessFragment)
                }
                State.ERROR ->{
                    loadingDialog.stop()
                    Toast.makeText(context, "Your Transfer Failed", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_transferConfirmationPinFragment2_to_transferFailedFragment)
                }
            }
        }
    }

    fun editPin(){
        pin.add(0,binding.pin1)
        pin.add(1,binding.pin2)
        pin.add(2,binding.pin3)
        pin.add(3,binding.pin4)
        pin.add(4,binding.pin5)
        pin.add(5,binding.pin6)
        pinHandler()
    }

    fun pinHandler(){
        for (i in 0..5){
            pin.get(i).addTextChangedListener(object : TextWatcher{

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    pin.get(i).setBackgroundResource(R.drawable.background_change_pin_after)
                }

                override fun afterTextChanged(s: Editable) {
                    if (i == 5 && pin[i].text.toString().isNotEmpty()) {
                        pin[i].clearFocus()
                        //Clears focus when you have entered the last digit of the pin.
                    } else if (pin[i].text.toString().isNotEmpty()) {
                        pin[i + 1].requestFocus() //focuses on the next edittext after a digit is entered.
                    }
                }
            })

            pin.get(i).setOnKeyListener(View.OnKeyListener{ view, x, keyEvent->
                if (keyEvent.action !== KeyEvent.ACTION_DOWN){
                    return@OnKeyListener false
                }
                if(x==KeyEvent.KEYCODE_DEL && pin.get(i).getText().toString().isEmpty() && i !=0){
                    pin.get(i-1).setText("")
                    pin.get(i-1).requestFocus()
                    pin.get(i).setBackgroundResource(R.drawable.background_change_pin)
                }
                false
            })
        }
    }

}