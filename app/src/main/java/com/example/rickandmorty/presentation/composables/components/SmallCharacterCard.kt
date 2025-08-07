package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus

@Composable
fun SmallCharacterCard(
    modifier: Modifier = Modifier,
    rickAndMortyCharacter: RickAndMortyCharacter
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
                model = rickAndMortyCharacter.image,
                contentDescription = stringResource(R.string.character_image, rickAndMortyCharacter.name),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder)
            )

            StatusComponent(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                character = rickAndMortyCharacter
            )
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
                    textAlign = TextAlign.Center,
                    text = rickAndMortyCharacter.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.text_name)
                )

                Spacer(
                    modifier = Modifier
                        .padding(5.dp)
                )

                Text(
                    textAlign = TextAlign.Center,
                    text = "${rickAndMortyCharacter.gender.displayName} | ${rickAndMortyCharacter.species}",
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

    val fakeRickAndMortyCharacter = RickAndMortyCharacter(
        id = 1,
        "Name",
        "Species",
        CharacterStatus.UNKNOWN,
        CharacterGender.GENDERLESS,
        "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SmallCharacterCard(rickAndMortyCharacter = fakeRickAndMortyCharacter)
    }
}