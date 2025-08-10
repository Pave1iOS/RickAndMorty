package com.example.rickandmorty.presentation.screens.mainScreen

import MainScreenContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.App
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.utils.NetworkMonitor
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems
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
            RickAndMortyTheme {
                MainScreenContainer(viewModel = viewModel)
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {

    val fakeList = List(6) {
        RickAndMortyCharacter(
            id = 1,
            name = "Character $it",
            status = CharacterStatus.ALIVE,
            species = "Human",
            gender = CharacterGender.UNKNOWN,
            image = ""
        )
    }

    val pagingItems = rememberFakeLazyPagingItems(fakeList)

    RickAndMortyTheme {
        MainScreenContent(
            rickAndMortyCharacters = pagingItems,
            searchQuery = "",
            onSearchQueryChange = {},
            onFilterChange = { _, _ -> }
        )
    }
}
