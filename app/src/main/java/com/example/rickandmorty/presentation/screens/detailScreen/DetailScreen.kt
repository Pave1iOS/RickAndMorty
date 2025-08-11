package com.example.rickandmorty.presentation.screens.detailScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.App
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenViewModel
import com.example.rickandmorty.theme.RickAndMortyTheme
import javax.inject.Inject

class DetailScreen: ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharacterDetailsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CharacterDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("characterId", -1)

        setContent {
            RickAndMortyTheme {
                val state by viewModel.state.collectAsState()

                LaunchedEffect(id) {
                    if (id != -1) {
                        viewModel.loadCharacter(id)
                    }
                }

                CharacterDetailsScreen(
                    state,
                    onBack = {}
                )
            }
        }
    }
}