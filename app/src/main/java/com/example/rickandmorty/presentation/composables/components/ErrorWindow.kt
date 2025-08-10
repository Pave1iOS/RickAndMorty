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
import com.example.rickandmorty.utils.ErrorType

@Composable
fun ErrorWindow(
    modifier: Modifier = Modifier,
    text: String = "",
    errorType: ErrorType = ErrorType.NETWORK,
    onDismiss: () -> Unit = {}
) {

    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val boxWidth = maxWidth * 0.8f
        val boxHeight = maxHeight * 0.4f

        val (
            buttonText,
            buttonColor,
            colorMessage
        ) = when(errorType) {
            ErrorType.NETWORK -> {
                Triple(
                    stringResource(R.string.close),
                    colorResource(R.color.black),
                    colorResource(R.color.error_message)
                )
            }
            ErrorType.DATA -> {
                Triple(
                    stringResource(R.string.close_filter),
                    colorResource(R.color.green_circle),
                    colorResource(R.color.black)
                )
            }
        }

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
                    text = text,
                    color = colorMessage
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(buttonColor)
                        .clickable { onDismiss() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
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
        ErrorWindow(
            text = "Hello",
            errorType = ErrorType.DATA
        )
    }
}