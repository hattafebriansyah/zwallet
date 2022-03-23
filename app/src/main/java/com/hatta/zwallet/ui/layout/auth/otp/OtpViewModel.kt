package com.hatta.zwallet.ui.layout.auth.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private val dataSource: ZWalletDataSource) : ViewModel() {

    fun otpActivation(email: String, otp: String): LiveData<Resource<APIResponse<User>?>> {
        return dataSource.otpActivation(email, otp)
    }

}