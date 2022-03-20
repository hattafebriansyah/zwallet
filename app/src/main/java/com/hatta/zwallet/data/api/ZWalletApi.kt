package com.hatta.zwallet.data.api


import com.hatta.zwallet.model.*
import com.hatta.zwallet.model.request.*
import retrofit2.Call
import retrofit2.http.*

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

    @POST("tranfer/newTranfer")
    suspend fun transfer(@Body Transfer: TransferRequest, @Header("x-access-PIN") pin:String):APIResponseTransfer<Transfer>

    @GET("auth/checkPIN/{pin}")
    suspend fun checkPin(@Path("pin") pin: Int): APIResponse<String>

    @PATCH("auth/PIN")
    suspend fun setPin(@Body request: SetPinRequest): APIResponse<String>

}