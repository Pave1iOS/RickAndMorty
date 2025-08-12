package com.example.rickandmorty.presentation.composables.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun LoadIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true
) {
    if (isLoading) {
        val rotation = rememberInfiniteTransition(label = "infiniteRotation")
        val angle by rotation.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotationAngle"
        )

        Column(
            modifier = modifier.size(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.icon_refresh),
                contentDescription = stringResource(R.string.refresh_content),
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        rotationZ = angle
                    }
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = stringResource(R.string.loading),
                color = colorResource(R.color.text_secondary)
            )
        }
    }
}



@Preview
@Composable
fun LoadIndicatorPreview() {
    LoadIndicator(isLoading = true)
}
