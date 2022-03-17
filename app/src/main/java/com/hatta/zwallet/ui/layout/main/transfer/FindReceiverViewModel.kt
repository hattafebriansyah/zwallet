package com.hatta.zwallet.ui.layout.main.transfer

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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindReceiverViewModel @Inject constructor( private var  dataSource : ZWalletDataSource): ViewModel(){

    fun getContactUser(): LiveData<Resource<APIResponse<List<ContactUser>>?>> {
        return dataSource.getContactUser()
    }
}