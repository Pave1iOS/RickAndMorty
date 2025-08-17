package com.example.rickandmorty.presentation.screens.mainScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.components.RememberSnackbarHost
import com.example.rickandmorty.presentation.composables.sections.CharacterFilterScreen
import com.example.rickandmorty.utils.FilterParams
import com.example.rickandmorty.utils.daggerViewModel
import com.example.rickandmorty.utils.isAppending
import com.example.rickandmorty.utils.isEmptyAfterLoad
import com.example.rickandmorty.utils.isError
import com.example.rickandmorty.utils.isFirstLoad
import com.example.rickandmorty.utils.isFullReload

@Composable
fun MainScreenContainer(
    onClick: (Int) -> Unit = {},
    gridState: LazyGridState
) {
    val viewModel: MainViewModel = daggerViewModel()

    val characters = viewModel.characters.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)
    val shouldScrollToTop by viewModel.shouldScrollToTop.collectAsState()

    var showFilter by remember { mutableStateOf(false) }

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

        RememberSnackbarHost(
            uiEvent = uiEvent,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

