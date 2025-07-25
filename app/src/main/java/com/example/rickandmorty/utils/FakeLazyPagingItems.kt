package com.example.rickandmorty.utils

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import kotlinx.coroutines.flow.flowOf

@Composable
fun rememberFakeLazyPagingItems(characters: List<RickAndMortyCharacter>): LazyPagingItems<RickAndMortyCharacter> {
    val fakePagingData = flowOf(PagingData.from(characters))
    return fakePagingData.collectAsLazyPagingItems()
}