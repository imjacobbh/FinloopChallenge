package com.jacob.finloopchallenge.data.db

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserbyId(id: Int): User

    @Insert
    suspend fun insert(users: List<User>)

    @Delete
    suspend fun delete(user: User)
}