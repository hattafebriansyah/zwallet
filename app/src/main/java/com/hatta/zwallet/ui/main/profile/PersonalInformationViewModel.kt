package com.hatta.zwallet.ui.main.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.Resource

class PersonalInformationViewModel (app: Application): ViewModel(){
    private val apiClient : ZWalletApi =  NetworkConfig(app).buildApi()
    private var  dataSource = ZWalletDataSource(apiClient)

    fun getBalance(): LiveData<Resource<APIResponse<List<Balance>>?>> {
        return dataSource.getBalance()
    }
}