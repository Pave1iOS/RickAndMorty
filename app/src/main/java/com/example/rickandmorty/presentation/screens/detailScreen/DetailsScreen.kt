package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
import com.example.rickandmorty.presentation.composables.components.EpisodeCard
import com.example.rickandmorty.presentation.composables.components.ErrorWindowDialog
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.components.PropertySection
import com.example.rickandmorty.presentation.composables.sections.EpisodeListSection
import com.example.rickandmorty.utils.FakeData

@Composable
fun DetailsScreen(
    state: CharacterDetailsState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    color = colorResource(R.color.text_name),
                    text = state.character?.name.orEmpty()
                ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            tint = colorResource(R.color.text_name),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                backgroundColor = colorResource(R.color.backgraund)

            )
        }
    ) { padding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    LoadIndicator()
                }
            }

            state.error != null -> {
                ErrorWindowDialog(
                    text = stringResource(R.string.error_loading_character),
                    onClick = onRetry
                )
            }

            state.character != null -> {
                val char = state.character

                Box(modifier = Modifier
                    .fillMaxSize()
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = char.image,
                        contentDescription = char.name,
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .padding(bottom = 15.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(RoundedCornerShape(15.dp))
                                .background(colorResource(R.color.backgraund).copy(alpha = 0.6f))
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f, fill = false),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    PropertySection(text = "Status: ${char.status}")
                                    PropertySection(text = "Species: ${char.species}")
                                    PropertySection(text = "Gender: ${char.gender}")
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                Column(
                                    modifier = Modifier.weight(1f, fill = false),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    PropertySection(text = "Origin: ${char.origin.name}")
                                    PropertySection(text = "Location: ${char.location.name}")
                                }
                            }
                        }

                        EpisodeListSection(
                            title = stringResource(R.string.episode_title),
                            episodes = char.episode
                        )

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        state = CharacterDetailsState(
            character = FakeData.CHARACTER.copy(
                name = "Rick Sanchez",
                status = StatusFilter.ALIVE,
                species = "Human",
                gender = GenderFilter.GENDERLESS,
                origin = FakeData.CHARACTER.origin.copy(name = "Earth (C-137)"),
                location = FakeData.CHARACTER.location.copy(name = "Citadel of Ricks"),
                episode = List(12) { "Episode ${it + 1}" }
            ),
            isLoading = false,
            error = null
        ),
        onBack = {},
        onRetry = {}
    )
}
