package com.jacob.finloopchallenge.di

import android.content.Context
import android.content.SharedPreferences
import com.jacob.finloopchallenge.AppConstants
import com.jacob.finloopchallenge.data.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            AppConstants.SHARED_NAME, Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun provideAppSharedPreferences(sharedPreferences: SharedPreferences) = Prefs(sharedPreferences)
}