package com.example.rickandmorty.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun provideAdaptiveMetrics(): AppMetrics {
    val config = LocalConfiguration.current
    val screenDp = config.screenWidthDp

    return AppMetrics(
        text = AppMetrics.TextMetrics(
            titleFontSize = 18.sp,
            bodyFontSize = 14.sp,
            captionFontSize = 10.sp
        ),
        spacing = AppMetrics.SpacingMetrics(
            extraSmall = 2.dp,
            small = 4.dp,
            medium = 8.dp,
            large = 16.dp
        ),
        image = AppMetrics.ImageMetrics(
            imageCardHeight = 120.dp,
            imageCardWidth = 48.dp,
            imageFullHeight = 8.dp,
            imageFullWidth = 200.dp,
            imageCategoryHeight = 40.dp,
            imageCategoryWidth = 40.dp
        ),
        card = AppMetrics.CardMetrics(
            cardHeight = 120.dp,
            cardWidth = 20.dp,
            cornerRadius = 12.dp,
            padding = 8.dp,
            border = 2.dp
        ),
        animation = AppMetrics.AnimationMetrics(
            fast = 100,
            medium = 200,
            slow = 400
        )
    )
}