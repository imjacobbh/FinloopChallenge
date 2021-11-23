package com.jacob.finloopchallenge.data.network

import com.jacob.finloopchallenge.FinloopChallengeApplication.Companion.connectionState
import com.jacob.finloopchallenge.domain.model.UserDetailsModel
import com.jacob.finloopchallenge.domain.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersService @Inject constructor(private val api: APIClient){

    suspend fun getUsersFromServer(): List<UserModel> {
        return withContext(Dispatchers.IO) {
            if(!connectionState()){
                return@withContext emptyList()
            }
            val response = api.getUsersList("users")
            response.body() ?: emptyList<UserModel>()
        }
    }
    suspend fun getUserDetailsFromServer(userId : Int): List<UserDetailsModel>{
        return withContext(Dispatchers.IO){
            if(!connectionState()){
                return@withContext emptyList()
            }
            val response = api.getUserDetails("users/$userId/userdetail")
            response.body() ?: emptyList<UserDetailsModel>()
        }


    }
}