package com.hatta.zwallet.data.api


import com.hatta.zwallet.model.*
import com.hatta.zwallet.model.request.LoginRequest
import com.hatta.zwallet.model.request.RefreshTokenRequest
import com.hatta.zwallet.model.request.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
     suspend fun login(@Body request: LoginRequest): APIResponse<User>

    @POST("auth/signup")
     fun register(@Body request: RegisterRequest): Call<APIResponse<User>>

    @GET("user/myProfile")
    fun getUserDetail():Call<APIResponse<UserDetail>>

    @GET("home/getBalance")
    suspend fun getBalance():APIResponse<List<Balance>>

    @GET("home/getInvoice")
    suspend fun getInvoice():APIResponse<List<Invoice>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<APIResponse<User>>


}