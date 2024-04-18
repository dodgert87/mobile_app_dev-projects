package com.example.movierecommendation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@Composable
fun MediaDetailScreen(item: String, navController: NavController) {
    val parsedValues = item.split(',')

    val itemId: Int = parsedValues[0].toInt()
    val mediaType = parsedValues[1]
    val tmdbRequest = TmdbRequest()

    val detailsState = remember { mutableStateOf<MediaDetails?>(null) }


    LaunchedEffect(key1 = itemId) {
            when(mediaType){
                "movie" -> tmdbRequest.fetchMovieDetails(itemId, success = {
                    detailsState.value = it
                }, failure = { error ->
                    Log.e("MovieDetails", "Failed to fetch movie details: ", error)
                })
                "tv" ->  tmdbRequest.fetchTvDetails(itemId, success = {
                    detailsState.value = it
                }, failure = { tvError ->
                    Log.e("TvDetails", "Failed to fetch Tv details: ", tvError)
                })
            }

    }

    MediaDetailContent(details = detailsState.value, navController)
}

@Composable
fun MediaDetailContent(details: MediaDetails?, navController: NavController) {
    if (details != null) {
        Column {
            ImageSection(imageUrl = details.posterPath, navController)
            DetailSection(details = details)
        }
    } else {
        Text("Loading details...")
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageSection(imageUrl: String?,navController: NavController) {
    Box{
        imageUrl?.let {
            val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500$imageUrl")
            Image(
                painter = painter,
                contentDescription = "Media Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                contentScale = ContentScale.Crop
            )
            BackButton(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun BackButton(navController: NavController, modifier: Modifier = Modifier) {

    IconButton(
        onClick = {
            navController.navigate(route = Screen.Home.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        },
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            .size(48.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Go back",
            tint = Color.White
        )
    }
}
@Composable
fun DetailSection(details: MediaDetails) {
    LazyColumn {
        item {
            Text(
                text = "Name: ${details.name}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        item { Text(text = "Release Date: ${details.releaseDate}") }
        item { Text(text = "Runtime: ${details.runtime} minutes") }
    }
}

