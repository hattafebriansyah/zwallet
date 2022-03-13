package com.hatta.zwallet.data

import androidx.lifecycle.liveData
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.Invoice
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.LoginRequest
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient

class ZWalletDataSource (private val apiClient: ZWalletApi) {
    fun login (email: String, password: String) = liveData<APIResponse<User>>(Dispatchers.IO) {
        try{
            val loginRequest = LoginRequest (email = email, password = password)
            val response = apiClient.login(loginRequest)
            emit(response)
        } catch (e: Exception){
            emit(APIResponse(400,e.localizedMessage, null))
        }
    }

    fun getInvoice() = liveData<APIResponse<List<Invoice>>>(Dispatchers.IO) {
        try {
            val response = apiClient.getInvoice()
            emit(response)
        } catch (e: Exception){
            emit(APIResponse(400,e.localizedMessage, null))
        }
    }

    fun getBalance() = liveData<APIResponse<List<Balance>>>(Dispatchers.IO) {
        try {
            val response = apiClient.getBalance()
            emit(response)
        } catch (e: Exception){
            emit(APIResponse(400,e.localizedMessage, null))
        }
    }

}