package com.example.rickandmorty.presentation.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R

@Composable
fun ErrorWindow(
    modifier: Modifier = Modifier,
    text: String = "",
    onDismiss: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val boxWidth = maxWidth * 0.8f
        val boxHeight = maxHeight * 0.4f

        Surface(
            modifier = Modifier
                .size(boxWidth, boxHeight),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFAFAFA),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(boxWidth * 0.4f, boxHeight * 0.4f),
                    painter = painterResource(R.drawable.sad_morty),
                    contentDescription = stringResource(R.string.sad_morty, text),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${stringResource(R.string.error)}: $text",
                    color = colorResource(id = R.color.error_message)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(R.color.black))
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.close),
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ErrorWindowPreview() {
    Box(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ErrorWindow()
    }
}