package com.example.movierecommendation


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp



@Composable
fun RegionAndTimezoneDropdownMenu(
    regions: List<Country>,
    selectedRegion: String,
    shapeColor: Color = Color.Black,
    onRegionSelected: (isoCode: String, englishName: String) -> Unit,
) {
    var expandedRegion by remember { mutableStateOf(false) }
    var selectedRegionText by remember { mutableStateOf(selectedRegion) }


    LaunchedEffect(selectedRegion) {
            selectedRegionText= selectedRegion
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Selected Region :", modifier = Modifier
            .padding(start = 15.dp)
            .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleMedium,
            )
        Box(modifier = Modifier
            .padding(8.dp),
            contentAlignment = Alignment.Center) {
            TextButton(
                onClick = { expandedRegion = true}) {
                Text(text = selectedRegionText,
                    color = shapeColor,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Icon(imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop-down arrow",
                    tint = shapeColor
                )
            }
            DropdownMenu(
                expanded = expandedRegion,
                onDismissRequest = { expandedRegion = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                regions.forEach { region ->
                    DropdownMenuItem(onClick = {
                        onRegionSelected(region.isoCode, region.englishName)
                        expandedRegion = false
                    }) {
                        Text(text = region.englishName)
                    }
                }
            }
        }
    }
}
