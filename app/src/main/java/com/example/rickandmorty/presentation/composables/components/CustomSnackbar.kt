package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 6.dp,
        color = colorResource(R.color.backgraund),
        modifier = modifier
            .padding(16.dp)
            .shadow(
                elevation = 3.dp,
                clip = false
            )
    ) {
        Text(
            text = snackbarData.visuals.message,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.text_name),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@Preview
@Composable
fun CustomSnackbarPreview() {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {

        val fakeSnackbarData = object : SnackbarData {
            override val visuals = object : SnackbarVisuals {
                override val message: String = "Hello from Snackbar!"
                override val actionLabel: String? = "OK"
                override val withDismissAction: Boolean = false
                override val duration = SnackbarDuration.Short
            }

            override fun dismiss() {}
            override fun performAction() {}
        }

        CustomSnackbar(fakeSnackbarData)
    }
}