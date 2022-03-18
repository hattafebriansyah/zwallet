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
import com.hatta.zwallet.utils.BASE_URL
import dagger.hilt.android.AndroidEntryPoint
import id.grinaldi.zwallet.model.request.TransferRequest

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

        binding.buttonContinueConfirmation.setOnClickListener {
            viewModel.setTransferParameter(TransferRequest(
                "",
                binding.amountTransfer.text.toString().toInt(),
                binding.noteTransfer.text.toString()
            ))

            Navigation.findNavController(view).navigate(R.id.action_transferFragment2_to_transferConfirmationFragment2)
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }


        viewModel.getSelectedContact().observe(viewLifecycleOwner) {
        var id: String?=null
            id = it?.id.toString()
            binding.apply {
                nameContact.text = "${it?.name}"
                phoneContact.text = "${it?.phone}"
                Glide.with(imageContact)
                    .load(BASE_URL + it?.image)
                    .apply(
                        RequestOptions.circleCropTransform()
                            .placeholder(R.drawable.ic_baseline_remove_red_eye_24)
                    )
                    .into(imageContact)
            }
        }
    }
}