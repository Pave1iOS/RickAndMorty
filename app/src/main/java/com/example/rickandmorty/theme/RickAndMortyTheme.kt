package com.example.rickandmorty.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun RickAndMortyTheme(content: @Composable () -> Unit) {
    val metrics = provideAdaptiveMetrics()

    val darkColorScheme = darkColorScheme(
        primary = Color(0xFF00C853),
        onPrimary = Color.Black,
        background = colorResource(R.color.backgraund),
        onBackground = Color.White,
        surface = colorResource(R.color.backgraund),
        onSurface = Color.White
    )

    CompositionLocalProvider(LocalAppMetrics provides metrics) {
        MaterialTheme(
            colorScheme = darkColorScheme
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .offset(y = (-2).dp)
                ) {
                    content()
                }
            }
        }
    }
}
