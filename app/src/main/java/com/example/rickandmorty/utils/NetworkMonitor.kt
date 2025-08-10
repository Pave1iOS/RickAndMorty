package com.example.rickandmorty.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

object NetworkMonitor {
    
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    enum class NetworkStatus {
        ONLINE,
        OFFLINE
    }

    fun getStatus(): NetworkStatus {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return NetworkStatus.OFFLINE
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkStatus.OFFLINE
        return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            NetworkStatus.ONLINE
        } else {
            NetworkStatus.OFFLINE
        }
    }

    fun getStatusFlow(): Flow<NetworkStatus> = callbackFlow {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        fun currentStatus(): NetworkStatus {
            val network = connectivityManager.activeNetwork ?: return NetworkStatus.OFFLINE
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkStatus.OFFLINE
            return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                NetworkStatus.ONLINE
            } else {
                NetworkStatus.OFFLINE
            }
        }

        trySend(currentStatus())

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.ONLINE)
            }

            override fun onLost(network: Network) {
                trySend(NetworkStatus.OFFLINE)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}
