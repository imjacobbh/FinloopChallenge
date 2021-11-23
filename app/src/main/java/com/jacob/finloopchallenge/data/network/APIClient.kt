package com.jacob.finloopchallenge.data.network

import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.domain.model.UserModel
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface APIClient {
    @GET
    suspend fun getUsersList(@Url url:String): Response<List<UserModel>>
    @GET
    suspend fun getUserDetails(@Url url:String) : Response<List<UserDetailsModel>>
}
