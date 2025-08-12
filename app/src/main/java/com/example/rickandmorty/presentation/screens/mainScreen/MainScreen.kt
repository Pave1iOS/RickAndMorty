package com.example.rickandmorty.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.presentation.composables.components.ButtonFilter
import com.example.rickandmorty.presentation.composables.components.CharacterCard
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.sections.SearchBar
import com.example.rickandmorty.utils.FakeData
import com.example.rickandmorty.utils.FakeData.rememberFakeLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    characters: LazyPagingItems<RickAndMortyCharacter>,
    query: String,
    onQueryChange: (String) -> Unit,
    onClick: (Int) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    isAppending: Boolean,
    onFilterClick: () -> Unit,
    gridState: LazyGridState
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            SearchBar(
                query = query,
                onQueryChange = onQueryChange,
                modifier = Modifier
                    .padding(8.dp)
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = onRefresh,
                modifier = Modifier
                    .weight(1f)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    items(characters.itemCount) { index ->
                        val character = characters[index]
                        if (character != null) {
                            CharacterCard(
                                character = character,
                                onClick = { onClick(character.id) }
                            )
                        }
                    }

                    if (isAppending) {
                        item(span = { GridItemSpan(2) }) {
                            LoadIndicator()
                        }
                    }
                }
            }
        }

        ButtonFilter(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = onFilterClick
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {

    val fakeRickAndMortyCharacter = FakeData.CHARACTER

    val charactersList = List(10) { index ->
        fakeRickAndMortyCharacter.copy(
            name = "Name $index",
            image = "https://rickandmortyapi.com/api/character/avatar/${index + 1}.jpeg"
        )
    }

    val pagingItems = rememberFakeLazyPagingItems(charactersList)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        MainScreen(
            characters = pagingItems,
            onClick = {},
            onRefresh = {},
            isRefreshing = false,
            isAppending = false,
            onFilterClick = {},
            query = "",
            onQueryChange = {},
            gridState = rememberLazyGridState()
        )
    }
}