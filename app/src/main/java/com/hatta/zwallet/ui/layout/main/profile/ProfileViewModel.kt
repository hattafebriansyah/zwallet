package com.hatta.zwallet.ui.layout.main.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.ManagePhoneRequest
import com.hatta.zwallet.model.request.SetPinRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (private var  dataSource : ZWalletDataSource): ViewModel(){

    fun getBalance(): LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }

    fun checkPin(pin: Int): LiveData<Resource<APIResponse<String>?>> {
        return dataSource.checkPin(pin)
    }

    fun setPin(request: SetPinRequest): LiveData<Resource<APIResponse<String>?>> {
        return dataSource.setPin(request)
    }

    fun managePhone(request: ManagePhoneRequest): LiveData<Resource<APIResponse<User>?>> {
        return dataSource.managePhone(request)
    }


}