package com.hatta.zwallet.ui.main.transfer

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.ContactUser
import com.hatta.zwallet.model.UserDetail
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.Resource

class FindReceiverViewModel (app: Application): ViewModel(){
    private val apiClient : ZWalletApi =  NetworkConfig(app).buildApi()
    private var  dataSource = ZWalletDataSource(apiClient)

    fun getContactUser(): LiveData<Resource<APIResponse<List<ContactUser>>?>> {
        return dataSource.getContactUser()
    }
}