package com.example.rickandmorty.utils

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.data.api.Location
import com.example.rickandmorty.data.api.Origin
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
import kotlinx.coroutines.flow.flowOf

object FakeData {

    val CHARACTER = RickAndMortyCharacter(
        id = 1,
        name = "Name",
        species = "Species",
        status = StatusFilter.UNKNOWN,
        gender = GenderFilter.GENDERLESS,
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        type = "",
        origin = Origin("", ""),
        location = Location("", ""),
        episode = emptyList(),
        url = "",
        created = ""
    )

    @Composable
    fun rememberFakeLazyPagingItems(characters: List<RickAndMortyCharacter>): LazyPagingItems<RickAndMortyCharacter> {
        val fakePagingData = flowOf(PagingData.from(characters))
        return fakePagingData.collectAsLazyPagingItems()
    }
}