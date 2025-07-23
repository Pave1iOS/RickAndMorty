package com.example.rickandmorty.data.DI

import android.app.Application
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreen
import dagger.BindsInstance
import dagger.Component

@Component(modules = [NetworkModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainScreen)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
