package com.example.rickandmorty.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.App
import com.example.rickandmorty.presentation.composables.AppNavHost
import com.example.rickandmorty.theme.RickAndMortyTheme
import javax.inject.Inject

class NavigationActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.get(this).appComponent.inject(this)

        setContent {
            RickAndMortyTheme {
                AppNavHost(viewModelFactory)
            }
        }
    }
}
