package com.jacob.finloopchallenge

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FinloopChallengeApplication : Application() {
    companion object {
        private lateinit var cm: ConnectivityManager
        private var activeNetwork: NetworkInfo? = null
        private var isConnected: Boolean = false

        fun connectionState(): Boolean{
            activeNetwork = cm.activeNetworkInfo
            return activeNetwork?.isConnectedOrConnecting == true
        }
    }

    override fun onCreate() {
        super.onCreate()
        cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        isConnected = activeNetwork?.isConnectedOrConnecting == true
    }

}