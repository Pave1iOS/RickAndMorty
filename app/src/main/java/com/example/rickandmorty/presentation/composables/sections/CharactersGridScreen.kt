package com.example.rickandmorty.presentation.composables.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.presentation.composables.components.SmallCharacterCard
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems

@Composable
fun CharactersGridScreen(
    modifier: Modifier = Modifier,
    rickAndMortyCharacters: LazyPagingItems<RickAndMortyCharacter>
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(rickAndMortyCharacters.itemCount) {
            val character = rickAndMortyCharacters[it]

            if (character != null)
            SmallCharacterCard(rickAndMortyCharacter = character)

        }
    }
}

@Preview
@Composable
fun CharactersGridScreenPreview() {

    val fakeRickAndMortyCharacter = RickAndMortyCharacter(
        name = "Name",
        species = "Species",
        status = "Status",
        gender = "Gender",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

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
        CharactersGridScreen(rickAndMortyCharacters = pagingItems)
    }
}