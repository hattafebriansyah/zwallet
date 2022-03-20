package com.hatta.zwallet.data

import androidx.lifecycle.liveData
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.request.LoginRequest
import com.hatta.zwallet.model.request.SetPinRequest
import com.hatta.zwallet.model.request.TransferRequest
import com.hatta.zwallet.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class ZWalletDataSource @Inject constructor(private val apiClient: ZWalletApi) {
    fun login (email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try{
            val loginRequest = LoginRequest (email = email, password = password)
            val response = apiClient.login(loginRequest)
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
            //emit(APIResponse(400,e.localizedMessage, null))
        }
    }

    fun getInvoice() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getInvoice()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
        }
    }

    fun getBalance() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getBalance()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
        }
    }

    fun getUserDetail() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getUserDetail()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
        }
    }

    fun getContactUser() = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.getContactUser()
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
        }
    }

    fun transfer(data: TransferRequest, pin: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.transfer(data,pin)
            emit(Resource.success(response))
        } catch (e: Exception){
            emit (Resource.error(null,e.localizedMessage))
        }
    }

    fun checkPin(pin: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            val response = apiClient.checkPin(pin)
            emit(Resource.success(response))
        }catch (e : java.lang.Exception){
            emit(Resource.error(null,e.localizedMessage))
        }
    }

    fun setPin(request: SetPinRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val response = apiClient.setPin(request)
            emit(Resource.success(response))
        } catch (e: java.lang.Exception){
            emit(Resource.error(null, e.localizedMessage))
        }
    }



}