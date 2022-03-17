package com.hatta.zwallet.ui.layout.auth.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.request.LoginRequest
import com.hatta.zwallet.network.NetworkConfig
import com.hatta.zwallet.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection
@HiltViewModel
class LoginViewModel @Inject constructor(private var dataSource : ZWalletDataSource) : ViewModel() {

    fun login(email: String, password: String): LiveData<Resource<APIResponse<User>?>> {
        return dataSource.login(email, password)
    }

}