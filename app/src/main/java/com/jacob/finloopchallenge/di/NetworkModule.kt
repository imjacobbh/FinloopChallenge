package com.jacob.finloopchallenge.di

import com.jacob.finloopchallenge.data.network.APIClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//activity
object NetworkModule {

    //RETROFIT
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://61959c9274c1bd00176c6df0.mockapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideAPIClient(retrofit: Retrofit): APIClient{
        return retrofit.create(APIClient::class.java)
    }
}