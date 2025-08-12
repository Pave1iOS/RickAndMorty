package com.example.rickandmorty.utils

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val LazyPagingItems<*>.isFirstLoad: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount == 0

val LazyPagingItems<*>.isFullReload: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount > 0

val LazyPagingItems<*>.isLoaded: Boolean
    get() = loadState.refresh is LoadState.NotLoading && itemCount > 0

val LazyPagingItems<*>.isAppending: Boolean
    get() = loadState.append is LoadState.Loading

val LazyPagingItems<*>.isError: Boolean
    get() = loadState.refresh is LoadState.Error

val LazyPagingItems<*>.isEmptyAfterLoad: Boolean
    get() = loadState.refresh is LoadState.NotLoading && itemCount == 0