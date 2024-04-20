package com.example.movierecommendation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movierecommendation.dataclass.Item


@Composable
fun CategoryRow(title: String, items: List<Item>, navController: NavController) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFFFFFFFF),
            modifier = Modifier.padding(10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow {
            items(items) { item ->
                MediaCard(
                    item = item,
                    onClick = { navController.navigate(route = "media_detail_screen/" + "${item.id},${item.mediaType}") })

            }
        }
    }
}