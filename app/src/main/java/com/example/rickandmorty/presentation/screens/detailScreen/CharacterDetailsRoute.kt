package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun CharacterDetailsRoute(
    characterId: Int,
    viewModel: DetailsViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    DetailsScreen(
        state = state,
        onBack = onBack
    )
}
