package com.jacob.finloopchallenge.di

import android.content.Context
import androidx.room.Room
import com.jacob.finloopchallenge.data.db.UserDao
import com.jacob.finloopchallenge.data.db.UserDb
import com.jacob.finloopchallenge.data.db.UserDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideUserDao(userDataBase: UserDb) : UserDao{
        return userDataBase.userDao()
    }
    @Provides
    fun provideUserDetailDao(userDataBase: UserDb): UserDetailDao{
        return userDataBase.userDetailDao()
    }
    @Provides
    @Singleton
    fun provideUserDataBase( @ApplicationContext context: Context) : UserDb {
        return Room.databaseBuilder(
            context,
            UserDb::class.java,
            "users"
        ).build()
    }
}