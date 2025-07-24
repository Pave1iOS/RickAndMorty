package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.utils.ContentDescriptions

@Composable
fun SmallCharacterCard(
    modifier: Modifier = Modifier,
    character: Character
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
                    .clip(RoundedCornerShape(8.dp)),
                model = character.image,
                contentDescription = ContentDescriptions.imageDescription(character.name),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(topStart = 12.dp))
                    .background(colorResource(R.color.black))
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(8.dp),
                    painter = painterResource(R.drawable.green_circle),
                    contentDescription = ContentDescriptions.statusDescription(character.status)
                )

                Text(
                    text = character.status,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth()
                .background(colorResource(R.color.secondary)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.text_name)
                )

                Spacer(
                    modifier = Modifier
                        .padding(5.dp)
                )

                Text(
                    text = "${character.gender} | ${character.species}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.text_secondary)
                )
            }

        }

    }
}


@Preview
@Composable
fun SmallCharacterCardPreview() {

    val fakeCharacter = Character(
        "Name",
        "Species",
        "status",
        "gender",
        "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SmallCharacterCard(character = fakeCharacter)
    }
}