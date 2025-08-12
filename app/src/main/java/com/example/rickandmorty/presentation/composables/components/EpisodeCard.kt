package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EpisodeCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {

    }
}

@Preview
@Composable
fun EpisodeCardPreview(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        EpisodeCard()
    }
}