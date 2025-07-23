package com.example.rickandmorty

import android.app.Application
import android.content.Context
import com.example.rickandmorty.data.DI.AppComponent
import com.example.rickandmorty.data.DI.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}