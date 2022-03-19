package com.hatta.zwallet.ui.layout.main.transfer

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentTransferSuccessBinding
import com.hatta.zwallet.ui.layout.main.MainActivity
import com.hatta.zwallet.utils.BASE_URL
import com.hatta.zwallet.utils.Helper.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
@AndroidEntryPoint
class TransferSuccessFragment : Fragment() {
    private lateinit var binding: FragmentTransferSuccessBinding
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareData()
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    fun prepareData(){

        viewModel.getSelectedContact().observe(viewLifecycleOwner){
            var id: String? = null
            id = it?.id.toString()
            binding.apply {
                transferToInfo.nameContact.text = "${it?.name}"
                transferToInfo.phoneContact.text = "${it?.phone}"
                Glide.with(transferToInfo.imageContact)
                    .load(BASE_URL + it?.image)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.ic_baseline_remove_red_eye_24)
                    )
                    .into(transferToInfo.imageContact)
            }
        }

        viewModel.getTransferParameter().observe(viewLifecycleOwner){
            binding.amountValue.formatPrice(it.amount.toString())
            if(it.notes.isNullOrEmpty()) {
                binding.notesValue.text = "-"
            } else {
                binding.notesValue.text = it.notes
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mma")
                val answer = current.format(formatter)
                binding.dateValue.text = answer
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("MMM dd, yyyy - HH:mma")
                val answer = formatter.format(date)
                binding.dateValue.text = answer
            }
            viewModel.getBalance().observe(viewLifecycleOwner) {
                binding.balanceValue.text = it.resource?.data?.get(0)?.balance.toString()
            }
        }


    }
}