package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composables.components.PropertySection
import com.example.rickandmorty.utils.FakeData

@Composable
fun CharacterDetailsScreen(
    state: CharacterDetailsState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.character?.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
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
                    CircularProgressIndicator()
                }
            }

            state.character != null -> {
                val char = state.character
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    AsyncImage(
                        model = char.image,
                        contentDescription = char.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f),
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                            .background(colorResource(R.color.backgraund))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                PropertySection(text = "Status: ${char.status}")
                                PropertySection(text = "Species: ${char.species}")
                                PropertySection(text = "Gender: ${char.gender}")
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                PropertySection(text = "Origin: ${char.origin.name}")
                                PropertySection(text = "Location: ${char.location.name}")
                                PropertySection(text = "Episodes: ${char.episode.size}")
                            }
                        }
                    }
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ошибка: ${state.error}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailScreenPreview() {
    CharacterDetailsScreen(
        state = CharacterDetailsState(character = FakeData.CHARACTER),
        onBack = {}
    )
}
