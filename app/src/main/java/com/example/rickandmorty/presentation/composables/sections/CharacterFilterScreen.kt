package com.example.rickandmorty.presentation.composables.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus

@Composable
fun CharacterFilterScreen(
    onApplyFilter: (status: String?, gender: String?) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedStatus by remember { mutableStateOf<String?>(null) }
    var selectedGender by remember { mutableStateOf<String?>(null) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val cardWidth = maxWidth * 0.9f
        val cardHeight = maxHeight * 0.4f

        Card(
            modifier = Modifier
                .width(cardWidth)
                .height(cardHeight),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Status", style = MaterialTheme.typography.titleMedium)
                    FlowRow(
                        maxItemsInEachRow = 4,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CharacterStatus.entries.forEach { status ->
                            FilterChip(
                                selected = selectedStatus == status.displayName,
                                onClick = { selectedStatus = status.displayName },
                                label = { Text(status.displayName) }
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier
                            .padding(
                                top = 5.dp,
                                bottom = 5.dp
                            )
                    )

                    Text("Gender", style = MaterialTheme.typography.titleMedium)
                    FlowRow(
                        maxItemsInEachRow = 4,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CharacterGender.entries.forEach { gender ->
                            FilterChip(
                                selected = selectedGender == gender.displayName,
                                onClick = { selectedGender = gender.displayName },
                                label = { Text(gender.displayName) }
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(R.color.secondary))
                        .padding(12.dp)
                        .clickable {
                            onApplyFilter(selectedStatus, selectedGender)
                            onDismiss()
                        }
                ) {
                    Text(
                        text = stringResource(R.string.apply_filter),
                        color = colorResource(R.color.text_name)
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun CharacterFilterScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CharacterFilterScreen(
            onApplyFilter = { _, _ -> },
            onDismiss = {}
        )
    }
}