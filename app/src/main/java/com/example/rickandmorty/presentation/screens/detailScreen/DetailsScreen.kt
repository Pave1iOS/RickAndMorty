package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.Episode
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
import com.example.rickandmorty.presentation.composables.components.PropertySection
import com.example.rickandmorty.presentation.composables.sections.EpisodeListSection
import com.example.rickandmorty.utils.FakeData

@Composable
fun DetailsScreen(
    character: RickAndMortyCharacter,
    episodes: List<Episode>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name, color = colorResource(R.color.text_name)) },
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
        },
        backgroundColor = colorResource(R.color.backgraund)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.backgraund))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = character.image,
                contentDescription = character.name,
                placeholder = painterResource(R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
                    .background(colorResource(R.color.backgraund).copy(alpha = 0.5f))
                    .zIndex(1f)
            )

            Column(
                modifier = modifier
                    .align(Alignment.BottomCenter)
                    .padding(padding)
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
                            PropertySection(text = "Status: ${character.status}")
                            PropertySection(text = "Species: ${character.species}")
                            PropertySection(text = "Gender: ${character.gender}")
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(
                            modifier = Modifier.weight(1f, fill = false),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            PropertySection(text = "Origin: ${character.origin.name}")
                            PropertySection(text = "Location: ${character.location.name}")
                        }
                    }
                }

                EpisodeListSection(
                    title = stringResource(R.string.episode_title),
                    episodeCount = character.episode.size,
                    episodes = episodes
                )

            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_5"
)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(
        character = FakeData.CHARACTER.copy(
            name = "Rick Sanchez",
            status = StatusFilter.ALIVE,
            species = "Human",
            gender = GenderFilter.GENDERLESS,
            origin = FakeData.CHARACTER.origin.copy(name = "Earth (C-137)"),
            location = FakeData.CHARACTER.location.copy(name = "Citadel of Ricks"),
            episode = List(12) { "Episode ${it + 1}" }
        ),
        episodes = List(10) { FakeData.EPISODE },
        onBack = {}
    )
}

