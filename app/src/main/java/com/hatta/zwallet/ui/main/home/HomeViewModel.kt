package com.hatta.zwallet.ui.main.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.Invoice
import com.hatta.zwallet.network.NetworkConfig

class HomeViewModel (app: Application) : ViewModel(){
    private val invoice = mutableListOf<List<Invoice>>()
    private val apiClient : ZWalletApi =  NetworkConfig(app).buildApi()
    private var  dataSource = ZWalletDataSource(apiClient)

    fun getInvoice(): LiveData<APIResponse<List<Invoice>>>{
        return dataSource.getInvoice()
    }
    fun getBalance(): LiveData<APIResponse<List<Balance>>>{
        return dataSource.getBalance()
    }

}