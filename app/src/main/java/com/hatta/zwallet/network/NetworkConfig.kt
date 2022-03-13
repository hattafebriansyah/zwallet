package com.hatta.zwallet.network

import android.content.Context
import com.ariefin.zwallet.network.TokenInterceptor
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.utils.BASE_URL
import com.hatta.zwallet.utils.KEY_USER_TOKEN
import com.hatta.zwallet.utils.PREFS_NAME
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig (val context: Context?){
    private val preferences = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

   private fun getInterceptor(authenticator: Authenticator? = null):OkHttpClient {
       val logging = HttpLoggingInterceptor()
       logging.level = HttpLoggingInterceptor.Level.BODY
       val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
       val token = prefs?.getString(KEY_USER_TOKEN, "")
       val client = OkHttpClient.Builder()
           .addInterceptor(logging)

       if (!token.isNullOrEmpty()) {
           client.addInterceptor(TokenInterceptor(token))
       }
       if (authenticator != null){
           client.authenticator(authenticator)
       }
       return client.build()
    }



    private fun getService(): ZWalletApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build().create(ZWalletApi::class.java)
    }

    fun buildApi(): ZWalletApi {
        val authenticator = RefreshTokenInterceptor(context, getService(), preferences!!)
        println("authenticator: ${authenticator.context}")
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getInterceptor(authenticator))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZWalletApi::class.java)
    }

}