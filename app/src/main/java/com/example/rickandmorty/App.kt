package com.example.rickandmorty

import android.app.Application
import android.content.Context
import com.example.rickandmorty.data.DI.AppComponent
import com.example.rickandmorty.data.DI.DaggerAppComponent
import com.example.rickandmorty.utils.NetworkMonitor

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        appComponent = DaggerAppComponent.factory().create(this)
        NetworkMonitor.init(this)
    }

    companion object {

        lateinit var instance: App
        private set

        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}