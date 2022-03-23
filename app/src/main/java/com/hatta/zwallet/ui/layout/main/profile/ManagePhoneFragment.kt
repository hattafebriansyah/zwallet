package com.hatta.zwallet.ui.layout.main.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.hatta.zwallet.R
import com.hatta.zwallet.databinding.FragmentChangePinNewBinding
import com.hatta.zwallet.databinding.FragmentManagePhoneBinding
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.ManagePhoneRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.ui.widget.LoadingDialog
import com.hatta.zwallet.utils.PREFS_NAME
import com.hatta.zwallet.utils.State
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ManagePhoneFragment : Fragment() {
    private lateinit var binding: FragmentManagePhoneBinding
    private lateinit var prefs : SharedPreferences
    private lateinit var loadingDialog: LoadingDialog
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagePhoneBinding.inflate(layoutInflater)
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setSoftInputMode((WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN))

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!
        loadingDialog = LoadingDialog(requireActivity())

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }


        binding.btnSubmit.setOnClickListener {

            val request = ManagePhoneRequest(
                binding.phone62.text.toString()+
                binding.phone.text.toString(),
                )

            viewModel.managePhone(request).observe(viewLifecycleOwner){
                when(it.state){
                    State.LOADING -> {
                        loadingDialog.start("Processing Your Request")
                    }
                    State.SUCCESS -> {
                        if (it.resource?.status == HttpsURLConnection.HTTP_OK) {
                            loadingDialog.stop()
                            Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view).popBackStack()
                        } else {
                            Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    State.ERROR -> {
                        loadingDialog.stop()
                        Toast.makeText(context, it.resource?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

//            NetworkConfig(context).buildApi().managePhone(managePhoneRequest)
//                .enqueue(object : Callback<APIResponse<User>> {
//                    override fun onResponse(
//                        call: Call<APIResponse<User>>,
//                        response: Response<APIResponse<User>>
//                    ) {
//                        if(response.body()?.status != HttpsURLConnection.HTTP_OK){
//                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
//                        }
//                        else{
//                            val res = response.body()!!.message
//                            Toast.makeText(context, "Manage Phone Number Success", Toast.LENGTH_SHORT).show()
//                            Navigation.findNavController(view).popBackStack()
//                        }
//                    }
//                    override fun onFailure(call: Call<APIResponse<User>>, t: Throwable) {
//                        Toast.makeText(context, "Failed to Manage Phone Number", Toast.LENGTH_SHORT).show()
//                    }
//                })
        }

    }
}