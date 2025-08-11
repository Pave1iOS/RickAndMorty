package com.example.rickandmorty.presentation.screens.mainScreen

import MainScreenContent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.theme.RickAndMortyTheme
import com.example.rickandmorty.utils.ErrorType
import com.example.rickandmorty.utils.LogSource
import com.example.rickandmorty.utils.NetworkMonitor
import com.example.rickandmorty.utils.rememberFakeLazyPagingItems

@Composable
fun MainScreenContainer(viewModel: MainScreenViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val networkStatus by viewModel.networkStatus.collectAsState()
    val characters = viewModel.characters.collectAsLazyPagingItems()

    val scaffoldState = rememberScaffoldState()
    var lastStatus by remember { mutableStateOf(networkStatus) }
    var showError by remember { mutableStateOf(false) }

    val onlineMessage = stringResource(R.string.online_message)
    val offlineMessage = stringResource(R.string.offline_message)

    val isFullReload = characters.loadState.refresh is LoadState.Loading && characters.itemCount == 0
    val isRefreshing = characters.loadState.refresh is LoadState.Loading && characters.itemCount > 0
    val isAppending = characters.loadState.append is LoadState.Loading
    val isError = characters.loadState.refresh is LoadState.Error

    var wasLoading by remember { mutableStateOf(false) }
    val isListEmptyAfterLoad = characters.loadState.refresh is LoadState.NotLoading &&
            characters.itemCount == 0 &&
            !wasLoading

    LaunchedEffect(characters.loadState.refresh) {
        wasLoading = characters.loadState.refresh is LoadState.Loading
    }

    LaunchedEffect(networkStatus) {
        when {
            lastStatus == NetworkMonitor.NetworkStatus.OFFLINE &&
                    networkStatus == NetworkMonitor.NetworkStatus.ONLINE -> {
                scaffoldState.snackbarHostState.showSnackbar(onlineMessage)
                characters.refresh()
            }
            lastStatus == NetworkMonitor.NetworkStatus.ONLINE &&
                    networkStatus == NetworkMonitor.NetworkStatus.OFFLINE -> {
                scaffoldState.snackbarHostState.showSnackbar(offlineMessage)
            }
        }
        lastStatus = networkStatus
    }

    LaunchedEffect(isError, isListEmptyAfterLoad) {
        if ((isError || isListEmptyAfterLoad) && !showError) {
            showError = true
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackbarHost(it) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(colorResource(R.color.backgraund))
        ) {
            when {
                isFullReload -> {
                    LoadIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                else -> {
                    MainScreenContent(
                        rickAndMortyCharacters = characters,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        onFilterChange = { status, gender ->
                            viewModel.setFilters(status, gender)
                        },
                        isRefreshing = isRefreshing
                    )
                }
            }

            if (isAppending) {
                LoadIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }

            if (showError) {
                ErrorWindowDialog(
                    text = stringResource(R.string.empty_list_message),
                    errorType = ErrorType.DATA,
                    onAction = {
                        viewModel.clearFilter()
                        showError = false
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun MainScreenContainerPreview() {
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
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            MainScreenContent(
                rickAndMortyCharacters = pagingItems,
                searchQuery = "",
                onSearchQueryChange = {},
                onFilterChange = { _, _ -> }
            )
        }
    }
}
