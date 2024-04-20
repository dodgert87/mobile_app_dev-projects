package com.example.movierecommendation.component


import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movierecommendation.R
import com.example.movierecommendation.ui.theme.Colors
import com.example.movierecommendation.dataclass.Country


@Composable
fun RegionAndTimezoneDropdownMenu(
    regions: List<Country>,
    selectedRegion: String,
    onRegionSelected: (isoCode: String, englishName: String) -> Unit,
) {
    var expandedRegion by remember { mutableStateOf(false) }
    var selectedRegionText by remember { mutableStateOf(selectedRegion) }


    LaunchedEffect(selectedRegion) {
        selectedRegionText = selectedRegion
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Text(
            text = stringResource(R.string.selected_region),
            color = Colors.textColor,
            modifier = Modifier
                .padding(start = 15.dp)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        )
        Box(
            modifier = Modifier
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = { expandedRegion = true }) {
                Text(
                    text = selectedRegionText,
                    color = Colors.textColor,
                    style = MaterialTheme.typography.titleMedium,
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Drop-down arrow",
                    tint = Colors.textColor,
                )
            }
            DropdownMenu(
                expanded = expandedRegion,
                onDismissRequest = { expandedRegion = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.background)
            ) {
                regions.forEach { region ->
                    DropdownMenuItem(onClick = {
                        onRegionSelected(region.isoCode, region.englishName)
                        expandedRegion = false
                    }) {
                        Text(
                            text = region.englishName,
                            color = Colors.textColor
                        )
                    }
                }
            }
        }
    }
}
