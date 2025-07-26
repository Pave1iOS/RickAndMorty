package com.example.rickandmorty.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.presentation.composables.components.ButtonFilter
import com.example.rickandmorty.presentation.composables.sections.CharactersGridScreen
import com.example.rickandmorty.presentation.composables.sections.SearchBar
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems

import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreenContent(
    rickAndMortyCharacters: LazyPagingItems<RickAndMortyCharacter>
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyGridState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            query = "",
            onQueryChange = {}
        )

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                isRefreshing = true
                coroutineScope.launch {
                    rickAndMortyCharacters.refresh()
                    while (rickAndMortyCharacters.loadState.refresh is LoadState.Loading) {
                        delay(100)
                    }
                    isRefreshing = false
                }
            }
        ) {
            CharactersGridScreen(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                rickAndMortyCharacters = rickAndMortyCharacters,
                gridState = gridState
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ButtonFilter(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 25.dp)
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
            status = "Alive",
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
