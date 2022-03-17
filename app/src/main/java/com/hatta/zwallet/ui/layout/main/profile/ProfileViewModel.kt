package com.hatta.zwallet.ui.layout.main.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (private var  dataSource : ZWalletDataSource): ViewModel(){

    fun getBalance(): LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }
}