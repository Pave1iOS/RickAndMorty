package com.example.rickandmorty.presentation.composables.sections

import com.example.rickandmorty.presentation.composables.components.EpisodeCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun EpisodeListSection(
    title: String,
    episodes: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.backgraund).copy(alpha = 0.5f))
    ) {
        Text(
            text = title,
            color = colorResource(R.color.text_name),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        BoxWithConstraints(
            modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(bottom = 10.dp)
        ) {

            val containerWidth = maxWidth * 0.7f

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(episodes) { episodeName ->
                    EpisodeCard(
                        modifier = Modifier
                            .width(containerWidth),
                        name = episodeName
                    )
                }
            }

        }


    }
}

@Preview
@Composable
fun EpisodeListSectionPreview() {

    val episodes = List(12) { "Episodes ${it + 1}" }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        EpisodeListSection("Episodes",episodes)
    }
}