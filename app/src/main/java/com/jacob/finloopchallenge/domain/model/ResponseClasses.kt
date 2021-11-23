package com.jacob.finloopchallenge.domain.model

data class UserModel(val createdAt : Int,
                     val name: String,
                     val country: String,
                     val id : Int)
data class UserDetailsModel(
    val jobtitle: String,
    val birthday: String,
    val salary: Float,
    val userId: Int)