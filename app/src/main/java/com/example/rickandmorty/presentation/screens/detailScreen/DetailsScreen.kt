package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.example.rickandmorty.presentation.composables.components.LoadIndicator
import com.example.rickandmorty.presentation.composables.components.PropertySection
import com.example.rickandmorty.utils.FakeData

@Composable
fun DetailsScreen(
    state: CharacterDetailsState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(R.color.backgraund),
                title = { Text(
                    color = colorResource(R.color.text_name),
                    text = state.character?.name.orEmpty())
                        },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            tint = colorResource(R.color.text_name),
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
                    LoadIndicator()
                }
            }

            state.character != null -> {
                val char = state.character

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = char.image,
                        contentDescription = char.name,
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(bottom = 15.dp)
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(15.dp))
                            .alpha(0.9f)
                            .background(colorResource(R.color.backgraund))
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f, fill = false),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.Start
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
    DetailsScreen(
        state = CharacterDetailsState(character = FakeData.CHARACTER),
        onBack = {}
    )
}
