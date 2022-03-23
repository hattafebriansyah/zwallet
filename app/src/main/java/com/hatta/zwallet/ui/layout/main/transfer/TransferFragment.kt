package com.hatta.zwallet.ui.layout.main.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentTransferBinding
import com.hatta.zwallet.model.request.TransferRequest
import com.hatta.zwallet.utils.BASE_URL
import com.hatta.zwallet.utils.Helper.formatPrice
import com.hatta.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransferFragment : Fragment() {
    private lateinit var binding: FragmentTransferBinding
    private val viewModel: TransferViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: String?=null

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        binding.buttonContinueConfirmation.setOnClickListener {
            viewModel.setTransferParameter(TransferRequest(
                id!!,
                binding.amountTransfer.text.toString().toInt(),
                binding.noteTransfer.text.toString()
            ))
            Navigation.findNavController(view).navigate(R.id.action_transferFragment2_to_transferConfirmationFragment2)
        }

        viewModel.getBalance().observe(viewLifecycleOwner) {
            binding.apply {
                when (it.state) {
                    State.LOADING -> {
                    }
                    State.SUCCESS -> {
                        binding.textAmountAvailable.formatPrice(it.resource?.data?.get(0)?.balance.toString())
                    }
                    State.ERROR -> {
                    }
                }
            }
        }

        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
            id = it?.id.toString()
            binding.apply {
                nameContact.text = "${it?.name}"
                phoneContact.text = "${it?.phone}"
                Glide.with(imageContact)
                    .load(BASE_URL + it?.image)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.ic_baseline_person_24)
                    )
                    .into(imageContact)
            }
        }
    }
}