package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composables.components.CustomSnackbar
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadIndicator()
                }
            }

            state.error != null -> {
                ErrorWindowDialog(
                    text = stringResource(R.string.error_loading_character),
                    onClick = onBack
                )
            }

            state.character != null -> {
                state.character?.let { character ->
                    DetailsScreen(
                        character = character,
                        episodes = state.episodes,
                        onBack = onBack,
                        modifier = Modifier
                    )
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


