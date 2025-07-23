package com.example.rickandmorty.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

class AppMetrics(
    val text: TextMetrics,
    val spacing: SpacingMetrics,
    val image: ImageMetrics,
    val card: CardMetrics,
    val animation: AnimationMetrics
) {
    data class TextMetrics(
        val titleFontSize: TextUnit,
        val bodyFontSize: TextUnit,
        val captionFontSize: TextUnit
    )

    data class SpacingMetrics(val extraSmall: Dp, val small: Dp, val medium: Dp, val large: Dp)

    data class ImageMetrics(
        val imageCardHeight: Dp,
        val imageCardWidth: Dp,
        val imageFullHeight: Dp,
        val imageFullWidth: Dp,
        val imageCategoryHeight: Dp,
        val imageCategoryWidth: Dp
    )

    data class CardMetrics(
        val cardHeight: Dp,
        val cardWidth: Dp,
        val cornerRadius: Dp,
        val padding: Dp,
        val border: Dp
    )
    data class AnimationMetrics(val fast: Int, val medium: Int, val slow: Int)
}

val LocalAppMetrics = staticCompositionLocalOf<AppMetrics> {
    error("AppMetrics not provided")
}