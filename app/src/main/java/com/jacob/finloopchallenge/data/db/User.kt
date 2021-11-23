package com.jacob.finloopchallenge.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity
data class User(
    val createdAt: Int,
    val name: String,
    val country: String,
    @PrimaryKey
    val id: Int
)