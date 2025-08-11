package com.example.rickandmorty.data.DI

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreen
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(activity: MainScreen)

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}
