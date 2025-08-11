package com.example.rickandmorty.presentation.composables.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.rickandmorty.R
import com.example.rickandmorty.utils.ErrorType
import com.example.rickandmorty.utils.LogSource

@Composable
fun ErrorWindowDialog(
    text: String,
    errorType: ErrorType = ErrorType.NETWORK,
    onAction: () -> Unit,
) {
    Dialog(onDismissRequest = onAction) {
        val (buttonText, buttonColor, colorMessage) = when (errorType) {
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
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFFAFAFA),
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(150.dp),
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
                        .clickable {
                            onAction()
                            Log.d(LogSource.INTERFACE, "ErrorWindowDialog - dialog close (button)")
                         },
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

        Dialog(
            onDismissRequest = {  }
        ) {

            ErrorWindowDialog(
                text = "Hello",
                errorType = ErrorType.DATA,
                onAction = {}
            )
        }

}