package com.jacob.finloopchallenge.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getUsersList(@Url url:String): Response<List<User>>
    @GET
    suspend fun getUserDetails(@Url url:String) : Response<List<UserDetails>>
}
