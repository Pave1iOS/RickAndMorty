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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.rickandmorty.R
import kotlinx.coroutines.delay

@Composable
fun RefreshIndicator(
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

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            visible = true
        } else {
            delay(1200)
            visible = false
        }
    }

    AnimatedVisibility(
        visible = visible,
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
