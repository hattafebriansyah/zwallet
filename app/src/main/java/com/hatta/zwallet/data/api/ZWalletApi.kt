package com.hatta.zwallet.data.api


import com.hatta.zwallet.model.*
import com.hatta.zwallet.model.request.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ZWalletApi {
    @POST("auth/login")
     suspend fun login(@Body request: LoginRequest): APIResponse<User>

    @POST("auth/signup")
     fun register(@Body request: RegisterRequest): Call<APIResponse<User>>

    @GET("home/getBalance")
    suspend fun getBalance():APIResponse<List<Balance>>

    @GET("home/getInvoice")
    suspend fun getInvoice():APIResponse<List<Invoice>>

    @GET("user/myProfile")
    suspend fun getUserDetail():APIResponse<UserDetail>

    @GET("tranfer/contactUser")
    suspend fun getContactUser():APIResponse<List<ContactUser>>

    @POST("auth/refresh-token")
    fun refreshToken(@Body request: RefreshTokenRequest): Call<APIResponse<User>>

    @PATCH("auth/PIN")
    fun createPIN(@Body request: CreatePinRequest): Call <APIResponse<User>>

    @PATCH("/user/changePassword")
    fun changePassword(@Body request: ChangePasswordRequest): Call <APIResponse<User>>

}