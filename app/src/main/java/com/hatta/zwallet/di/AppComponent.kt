package com.hatta.zwallet.di

import android.content.Context
import com.hatta.zwallet.data.ZWalletDataSource
import com.hatta.zwallet.data.api.ZWalletApi
import com.hatta.zwallet.network.NetworkConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponent {
    @Provides
    @Singleton
    fun provideAPI(@ApplicationContext context : Context): ZWalletApi = NetworkConfig(context)
        .buildApi()

    @Provides
    @Singleton
    fun provideDataSource (apiClient : ZWalletApi) : ZWalletDataSource = ZWalletDataSource(apiClient)

}