package com.example.movierecommendation

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier

@Composable
fun StarRating(modifier: Modifier = Modifier, rating: Double, maxRating: Int = 5) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            when {
                i <= rating.toInt() -> {
                    Icon(Icons.Filled.Star, contentDescription = "Filled Star", tint = Colors.darkBlueBlack)
                }
                i - rating > 0 && i - rating < 1 -> {
                    Icon(Icons.AutoMirrored.Filled.StarHalf, contentDescription = "Half Star", tint = Colors.darkBlueBlack)
                }
                else -> {
                    Icon(Icons.Outlined.StarOutline, contentDescription = "Empty Star", tint = Colors.lightGray)
                }
            }
        }
    }
}
