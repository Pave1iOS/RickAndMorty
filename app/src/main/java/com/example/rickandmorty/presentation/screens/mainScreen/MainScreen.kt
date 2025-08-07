package com.example.rickandmorty.presentation.screens.mainScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.App
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.presentation.composables.MainScreenContent
import com.example.rickandmorty.presentation.composables.components.ErrorWindow
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.presentation.composables.sections.CharacterFilterScreen
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

            val isFiltered by viewModel.isFiltered.collectAsState()

            val characters = if (isFiltered) {
                viewModel.filteredCharacters.collectAsLazyPagingItems()
            } else {
                viewModel.charactersPagingFlow.collectAsLazyPagingItems()
            }

            val isAppending = characters.loadState.append is LoadState.Loading
            val isError = characters.loadState.refresh is LoadState.Error

            val rickAndMortyCharacters = viewModel.rickAndMortyCharacters.collectAsLazyPagingItems()

            RickAndMortyTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    when {

                        isError -> {

                            val error = characters.loadState.refresh as LoadState.Error

                            ErrorWindow(text = error.error.localizedMessage
                                ?: stringResource(R.string.unknown_error))
                        }

                        else -> {
                            MainScreenContent(
                                rickAndMortyCharacters = characters,
                                onApplyFilter = { status, gender ->

                                    if(status == null && gender == null) {
                                        viewModel.clearFilter()
                                    } else {
                                        viewModel.filterCharacters(status, gender)
                                    }
                                }
                            )
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

 @Preview
 @Composable
 fun MainScreenPreview() {

     val fakeList = List(6) {
         RickAndMortyCharacter(
             id = it,
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
             onApplyFilter = { _, _ -> }
         )
     }
 }



