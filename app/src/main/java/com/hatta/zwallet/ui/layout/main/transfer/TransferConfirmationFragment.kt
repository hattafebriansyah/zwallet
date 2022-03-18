package com.hatta.zwallet.ui.layout.main.transfer

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentTransferConfirmationBinding
import com.hatta.zwallet.utils.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import com.hatta.zwallet.utils.Helper.formatPrice

@AndroidEntryPoint
class TransferConfirmationFragment : Fragment() {
    private lateinit var binding: FragmentTransferConfirmationBinding
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferConfirmationBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
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

            viewModel.getTransferParameter().observe(viewLifecycleOwner) {
                binding.amountValue.formatPrice(it.amount.toString())
                binding.balanceValue.formatPrice(it.amount.toString())
                if (it.notes.isNullOrEmpty()) {
                    binding.notesValue.text = "-"
                } else {
                    binding.notesValue.text = it.notes
                }
                // format date
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
            }
        }
    }}