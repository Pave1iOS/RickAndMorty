package com.example.rickandmorty.presentation.screens.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.App
import com.example.rickandmorty.presentation.composables.MainScreenContent
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

            val isRefreshing = characters.loadState.refresh is LoadState.Loading
            val isAppending = characters.loadState.append is LoadState.Loading
            val isError = characters.loadState.refresh is LoadState.Error

            RickAndMortyTheme {
                when(characters.loadState.refresh) {
                    is LoadState.Loading -> {
                        Box(modifier = Modifier
                            .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Загрузка...")
                        }

                        CircularProgressIndicator(modifier = Modifier
                            .fillMaxWidth()
                        )
                    }
                    is LoadState.Error -> {

                        val error = characters.loadState.refresh as LoadState.Error

                        Text(
                            text = "Ошибка загрузки: ${error.error.localizedMessage}",
                            color = Color.Red
                        )
                        Button(onClick = { characters.retry() }) {
                            Text("Повторить попытку")
                        }
                    }
                    else -> {
                        MainScreenContent(characters)
                    }
                }
            }

        }
    }
}



