package com.jacob.finloopchallenge.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetail(
    val jobtitle: String,
    val birthday: String,
    val salary: Float,
    @PrimaryKey
    val userId: Int)