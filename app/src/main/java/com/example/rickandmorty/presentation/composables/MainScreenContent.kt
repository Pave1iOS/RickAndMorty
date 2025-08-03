package com.example.rickandmorty.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.presentation.composables.components.ButtonFilter
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.components.RefreshIndicator
import com.example.rickandmorty.presentation.composables.sections.CharactersGridScreen
import com.example.rickandmorty.presentation.composables.sections.SearchBar
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun MainScreenContent(
    rickAndMortyCharacters: LazyPagingItems<RickAndMortyCharacter>,
    buttonFilterOnClick: () -> Unit = {}
) {

    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val isFirsLoad = rickAndMortyCharacters.loadState.refresh is LoadState.Loading
            && rickAndMortyCharacters.itemCount == 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth(),
            query = "",
            onQueryChange = {}
        )

        Spacer(modifier =
            Modifier
                .size(10.dp)
        )

        if (isFirsLoad) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadIndicator()
            }
        } else {
            SwipeRefresh(
                state = rememberSwipeRefreshState(
                    rickAndMortyCharacters.loadState.refresh is LoadState.Loading
                ),
                onRefresh = {
                    coroutineScope.launch {
                        rickAndMortyCharacters.refresh()
                    }
                },
                indicator = { state, _ ->
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(top = 12.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        RefreshIndicator(
                            isRefreshing = state.isRefreshing
                        )
                    }
                }
            ) {
                CharactersGridScreen(
                    modifier = Modifier
                        .fillMaxWidth(),
                    rickAndMortyCharacters = rickAndMortyCharacters,
                    gridState = gridState
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ButtonFilter(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 25.dp),
            onClick = buttonFilterOnClick
        )
    }
}



@Preview
@Composable
fun MainScreenContentPreview() {

    val fakeRickAndMortyCharacters = List(6) {
        RickAndMortyCharacter(
            name = "Name $it",
            species = "Human",
            status = CharacterStatus.ALIVE,
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/${it + 1}.jpeg"
        )
    }

    val pagingItems = rememberFakeLazyPagingItems(fakeRickAndMortyCharacters)

    RickAndMortyTheme {

        val topInset = WindowInsets.statusBars.asPaddingValues()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(topInset),
            contentAlignment = Alignment.Center
        ) {
            MainScreenContent(rickAndMortyCharacters = pagingItems)
        }
    }

}
