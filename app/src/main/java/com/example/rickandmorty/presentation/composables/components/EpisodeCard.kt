package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun EpisodeCard(
    modifier: Modifier = Modifier,
    episodeName: String,
    episodeNumber: String
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                clip = false
            )
            .clip(RoundedCornerShape(10.dp))
            .border(
                BorderStroke(1.dp, colorResource(R.color.secondary)),
                RoundedCornerShape(10.dp)
            )
            .background(colorResource(R.color.black))
    ) {

        Image(
            modifier = Modifier
                .matchParentSize()
                .alpha(0.2f),
            painter = painterResource(R.drawable.episode_bg),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.episode_descr, episodeName)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier,
                text = episodeName,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.text_name),
                fontWeight = FontWeight.Bold
            )

            Divider(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(0.7f),
                color = colorResource(R.color.secondary),
                thickness = 2.dp
            )

            Text(
                modifier = Modifier,
                text = episodeNumber,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.text_name)
            )

        }
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
        EpisodeCard(
            episodeName = "Kiting OFF ON Stane",
            episodeNumber = "S01E02"
        )
    }
}