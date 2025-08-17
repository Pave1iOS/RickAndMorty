package com.example.rickandmorty.presentation.composables.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.rickandmorty.utils.UiEvent
import kotlinx.coroutines.launch

@Composable
fun RememberSnackbarHost(
    uiEvent: UiEvent?,
    modifier: Modifier = Modifier
): SnackbarHostState {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    uiEvent?.let { event ->
        if (event is UiEvent.ShowMessage) {
            val message = stringResource(id = event.messageRes)
            LaunchedEffect(message) {
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data -> CustomSnackbar(data) },
        modifier = modifier
    )

    return snackbarHostState
}
