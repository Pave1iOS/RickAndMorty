package com.example.rickandmorty.presentation.screens.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.App
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composables.MainScreenContent
import com.example.rickandmorty.presentation.composables.components.ErrorWindow
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.theme.RickAndMortyTheme
import javax.inject.Inject

class MainScreen : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainScreenViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        App.get(this).appComponent.inject(this)

        setContent {

            val characters = viewModel.charactersPagingFlow.collectAsLazyPagingItems()

            val isLoading = characters.loadState.refresh is LoadState.Loading
            val isAppending = characters.loadState.append is LoadState.Loading
            val isError = characters.loadState.refresh is LoadState.Error

            RickAndMortyTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when {
                        isLoading -> {
                            LoadIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        isError -> {

                            val error = characters.loadState.refresh as LoadState.Error

                            ErrorWindow(text = error.error.localizedMessage
                                ?: stringResource(R.string.unknown_error))
                        }

                        else -> {
                            MainScreenContent(characters)
                        }
                    }

                    if (isAppending) {
                        LoadIndicator(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
            }
        }
    }
}



