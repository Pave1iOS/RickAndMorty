package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.utils.CharacterStatus

@Composable
fun StatusComponent(
    modifier: Modifier = Modifier,
    character: RickAndMortyCharacter
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 12.dp))
            .background(colorResource(R.color.black))
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        val statusIcon = colorResource(id = character.status.colorResID)

        Image(
            modifier = Modifier
                .size(8.dp),
            painter = rememberVectorPainter(
                image = ImageVector.vectorResource(R.drawable.ic_light_circle)
            ),
            colorFilter = ColorFilter.tint(statusIcon),
            contentDescription = stringResource(R.string.character_status, character.status)
        )

        Text(
            text = character.status.value,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
fun StatusComponentPreview() {
    val characters = listOf(
        RickAndMortyCharacter(
            name = "1",
            status = CharacterStatus.DEAD,
            species = "Human",
            gender = "Male",
            image = ""
        ),
        RickAndMortyCharacter(
            name = "2",
            status = CharacterStatus.ALIVE,
            species = "Human",
            gender = "Male",
            image = ""
        ),
        RickAndMortyCharacter(
            name = "3",
            status = CharacterStatus.UNKNOWN,
            species = "Human",
            gender = "Male",
            image = ""
        )

    )
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        StatusComponent(character = characters[0])
        StatusComponent(character = characters[1])
        StatusComponent(character = characters[2])
    }
}