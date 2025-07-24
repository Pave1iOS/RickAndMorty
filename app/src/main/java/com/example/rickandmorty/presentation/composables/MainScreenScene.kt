package com.example.rickandmorty.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.presentation.composables.components.ButtonFilter
import com.example.rickandmorty.presentation.composables.sections.CharactersGridScreen
import com.example.rickandmorty.presentation.composables.sections.SearchBar
import com.example.rickandmorty.theme.RickAndMortyTheme

@Composable
fun MainScreenContent(
    characters: List<Character>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        SearchBar(
            modifier = Modifier
                .padding(top = 5.dp),
            query = "",
            onQueryChange = {}
        )

        Box(
            modifier = Modifier
        ) {
            CharactersGridScreen(
                modifier = Modifier
                    .padding(top = 10.dp),
                characters = characters
            )


            ButtonFilter(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 25.dp, bottom = 25.dp)
            )
        }

    }
}

@Preview
@Composable
fun MainScreenContentPreview() {

    val fakeCharacters = List(6) {
        Character(
            name = "Name $it",
            species = "Human",
            status = "Alive",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/${it + 1}.jpeg"
        )
    }

    RickAndMortyTheme {

        val topInset = WindowInsets.statusBars.asPaddingValues()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(topInset),
            contentAlignment = Alignment.Center
        ) {
            MainScreenContent(characters = fakeCharacters)
        }
    }

}
