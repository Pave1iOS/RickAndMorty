package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.rickandmorty.utils.UiEvent
import com.example.rickandmorty.utils.daggerViewModel

@Composable
fun DetailsScreenContainer(
    characterId: Int,
    onBack: () -> Unit
) {
    val viewModel: DetailsViewModel = daggerViewModel()

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiEvent by viewModel.uiEvent.collectAsState(initial = null)

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    uiEvent?.let { event ->
        if (event is UiEvent.ShowMessage) {
            val message = stringResource(id = event.messageRes)
            LaunchedEffect(message) {
                snackbarHostState.showSnackbar(message)
            }
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
            DetailsScreen(
                state = state,
                onBack = onBack,
                onRetry = { viewModel.loadCharacter(characterId) }
            )
        }
    }
}
