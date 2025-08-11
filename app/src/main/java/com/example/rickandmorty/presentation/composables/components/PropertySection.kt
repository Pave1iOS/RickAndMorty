package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun PropertySection(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, colorResource(R.color.secondary)),
                RoundedCornerShape(15.dp)
            )
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = text.lowercase(),
            color = colorResource(R.color.text_name)
        )
    }
}

@Preview
@Composable
fun PropertySectionPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PropertySection(
            modifier = Modifier,
            text = "GENDER"
        )
    }
}