package com.hatta.zwallet.data.api


import com.hatta.zwallet.model.APIResponse
import com.hatta.zwallet.model.Balance
import com.hatta.zwallet.model.User
import com.hatta.zwallet.model.UserDetail
import com.hatta.zwallet.model.request.LoginRequest
import com.hatta.zwallet.model.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
     fun login(@Body request: LoginRequest): Call<APIResponse<User>>

    @POST("auth/signup")
     fun register(@Body request: RegisterRequest): Call<APIResponse<User>>

    @GET("user/myProfile")
    fun getUserDetail():Call<APIResponse<UserDetail>>

    @GET("home/getBalance")
    fun getBalance():Call<APIResponse<List<Balance>>>


}