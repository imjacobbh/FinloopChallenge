package com.jacob.finloopchallenge.data

import java.io.Serializable

//data class UsersResponse(val usersList: List<User>)
data class User(val createdAt : Int, val name: String, val country: String, val id : Int)
data class UserDetails( val jobtitle: String, val birthday: String, val salary: Float, val userId: Int)