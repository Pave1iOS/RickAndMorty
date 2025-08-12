package com.example.rickandmorty.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.sections.CharacterFilterScreen
import com.example.rickandmorty.utils.*
import kotlinx.coroutines.launch

@Composable
fun MainScreenContainer(
    onClick: (Int) -> Unit = {}
) {
    val viewModel: MainViewModel = daggerViewModel()

    val characters = viewModel.characters.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val status by viewModel.statusFilter.collectAsState()
    val gender by viewModel.genderFilter.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)
    val coroutineScope = rememberCoroutineScope()

    var showFilter by remember { mutableStateOf(false) }
    var loadIndicator by remember { mutableStateOf(true) }

    val gridState = rememberLazyGridState()

    uiEvent?.let { event ->
        if (event is UiEvent.ShowMessage) {
            val message = stringResource(id = event.messageRes)
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    LaunchedEffect(status, gender, query) {
        coroutineScope.launch {
            gridState.animateScrollToItem(0)
        }
    }

    LaunchedEffect(characters.isLoaded) {
        if (characters.isLoaded) {
            loadIndicator = false
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                characters.isFirstLoad -> LoadIndicator(
                    isLoading = loadIndicator
                )
                characters.isError -> ErrorWindowDialog(
                    text = "Ошибка загрузки данных",
                    onClick = { characters.retry() }
                )
                characters.isEmptyAfterLoad -> ErrorWindowDialog(
                    text = "Список пуст",
                    onClick = {
                        viewModel.setStatusFilter(null)
                        viewModel.setGenderFilter(null)
                    }
                )

                else -> MainScreen(
                    characters = characters,
                    query = query,
                    onQueryChange = { viewModel.setSearchQuery(it) },
                    onClick = onClick,
                    onRefresh = {
                        viewModel.resetFilters()
                        characters.refresh()
                                },
                    isRefreshing = characters.isFullReload,
                    isAppending = characters.isAppending,
                    onFilterClick = { showFilter = true },
                    gridState = gridState
                )
            }

            if (showFilter) {
                Dialog(onDismissRequest = { showFilter = false }) {
                    CharacterFilterScreen { status, gender ->
                        val params = FilterParams(status, gender, viewModel.searchQuery.value)

                        if (params.isReset) {
                            viewModel.resetFilters()
                        } else {
                            viewModel.setStatusFilter(status)
                            viewModel.setGenderFilter(gender)
                        }
                        showFilter = false
                    }
                }
            }
        }
    }
}

