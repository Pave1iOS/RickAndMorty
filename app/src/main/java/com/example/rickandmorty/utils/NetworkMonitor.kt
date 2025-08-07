package com.example.rickandmorty.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.rickandmorty.App

object NetworkMonitor {

    enum class NetworkStatus {
        ONLINE,
        OFFLINE
    }

    private var lastLoggedStatus: NetworkStatus? = null

    fun getStatus(): NetworkStatus {
        val context = App.instance.getAppContext()
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return NetworkStatus.OFFLINE
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkStatus.OFFLINE
        return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            NetworkStatus.ONLINE
        } else {
            NetworkStatus.OFFLINE
        }
    }

    fun logStatus(tag: String = "NetworkMonitor") {
        val current = getStatus()
        if (current != lastLoggedStatus) {
            Log.i(tag, "${LogSource.NETWORK} is ${current.name.lowercase()}")
            lastLoggedStatus = current
        }
    }
}
