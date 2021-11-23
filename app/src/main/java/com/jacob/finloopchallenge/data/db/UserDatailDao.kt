package com.jacob.finloopchallenge.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDetailDao{
    @Query("SELECT * FROM UserDetail")
    suspend fun getAll(): List<UserDetail>

    @Query("SELECT * FROM UserDetail WHERE userId = :id")
    suspend fun getUserbyId(id: Int): UserDetail?

    @Insert
    suspend fun insert(users: List<UserDetail>)

    @Delete
    suspend fun delete(user: UserDetail)
}