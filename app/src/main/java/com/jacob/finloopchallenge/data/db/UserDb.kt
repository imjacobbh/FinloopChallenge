package com.jacob.finloopchallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [User::class, UserDetail::class],
    version = 1
)

abstract class UserDb: RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun userDetailDao(): UserDetailDao
}