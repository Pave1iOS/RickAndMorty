package com.example.rickandmorty.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.presentation.composables.components.CustomSnackbar
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.sections.CharacterFilterScreen
import com.example.rickandmorty.utils.*
import kotlinx.coroutines.launch

@Composable
fun MainScreenContainer(
    onClick: (Int) -> Unit = {},
    gridState: LazyGridState
) {
    val viewModel: MainViewModel = daggerViewModel()

    val characters = viewModel.characters.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val query by viewModel.searchQuery.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)
    val shouldScrollToTop by viewModel.shouldScrollToTop.collectAsState()


    var showFilter by remember { mutableStateOf(false) }

    uiEvent?.let { event ->
        if (event is UiEvent.ShowMessage) {
            val message = stringResource(id = event.messageRes)
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    LaunchedEffect(shouldScrollToTop) {
        if (shouldScrollToTop) {
            gridState.animateScrollToItem(0)
            viewModel.shouldScrollToTop.value = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            characters.isFirstLoad -> LoadIndicator()

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

        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data -> CustomSnackbar(data) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

