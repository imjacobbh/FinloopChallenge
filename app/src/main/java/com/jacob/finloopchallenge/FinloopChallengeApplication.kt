package com.jacob.finloopchallenge

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.jacob.finloopchallenge.data.Prefs

class FinloopChallengeApplication : Application() {
    companion object {
        lateinit var prefs: Prefs
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
        prefs = Prefs(this)
        cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        isConnected = activeNetwork?.isConnectedOrConnecting == true
    }

}