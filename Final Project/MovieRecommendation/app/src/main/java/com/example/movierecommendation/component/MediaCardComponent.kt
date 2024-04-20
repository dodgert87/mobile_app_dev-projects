package com.example.movierecommendation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movierecommendation.R
import com.example.movierecommendation.dataclass.Item

@Composable
fun pixelsToDp(pixels: Int): Float {
    val density = LocalDensity.current.density
    return pixels / density
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MediaCard(item: Item, onClick: () -> Unit) {
    val imageWidthDp = pixelsToDp(360).dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFc3c3e6),
            ),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .width(imageWidthDp)
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

        ) {
            Column {
                item.posterPath?.let {
                    Image(
                        painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${item.posterPath}"),
                        contentDescription = item.mediaName,
                        modifier = Modifier
                            .height(190.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    text = item.shortenMediaName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp)
                )

                Text(
                    text = stringResource(R.string.media_type, item.mediaType),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "${item.releaseDate}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        StarRating(rating = item.voteAverage / 2)
    }
}
