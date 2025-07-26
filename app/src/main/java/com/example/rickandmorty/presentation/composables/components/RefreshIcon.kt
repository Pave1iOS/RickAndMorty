package com.example.rickandmorty.presentation.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun RefreshIcon(
    isRefreshing: Boolean
) {
    val rotation = rememberInfiniteTransition()
    val angle by rotation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        )
    )

    AnimatedVisibility(
        visible = isRefreshing,
        enter = scaleIn(initialScale = 0f) + fadeIn(),
    ) {
        Image(
            painter = painterResource(R.drawable.icon_refresh),
            contentDescription = stringResource(R.string.refresh_content),
            modifier = Modifier
                .graphicsLayer {
                    rotationZ = angle
                }
        )
    }
}
