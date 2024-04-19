package com.example.movierecommendation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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
        when (mediaType) {
            "movie" -> tmdbRequest.fetchMovieDetails(itemId, success = {
                detailsState.value = it
            }, failure = { error ->
                Log.e("MovieDetails", "Failed to fetch movie details: ", error)
            })

            "tv" -> tmdbRequest.fetchTvDetails(itemId, success = {
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
            Spacer(modifier = Modifier.height(10.dp))
            DetailSection(details = details)
        }
    } else {
        Text("Loading details...")
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageSection(imageUrl: String?, navController: NavController) {
    Box {
        imageUrl?.let {
            val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500$imageUrl")
            Image(
                painter = painter,
                contentDescription = "Media Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(580.dp),
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E0342),
                        Color(0xFF0f3d7a)
                    )
                )
            )
    ) {
        item {
            Text(
                text = details.name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = Colors.cyan,
                textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
            )
        }
        item {
            DetailRow("Release Date", details.releaseDate.toString())
        }
        if (details.episodeRunTime != null){
            item {
                DetailRow(title = "Run Time", information = details.episodeRunTime.toString())
            }
        }
        item { DetailRow(title = "Rating", information = details.voteAverage.toString())
        }
        item { DetailRow(title = "Number Of Votes", information = details.voteCount.toString()) }
        if (details.numberOfSeasons != null && details.numberOfEpisodes != null) {
            item { DetailRow(title = "Season", information = details.numberOfSeasons.toString()) }
            item { DetailRow(title = "Episode", information = details.numberOfEpisodes.toString()) }
        }
            item{DetailRowForListsData(title = "genres", details.genresName)}
        item{ DetailRowForListsData(title = "Language", informationList = details.languageList)}
        item { Reviews(title = "Reviews", review = details.reviews) }
    }
}

@Composable
fun DetailHeading(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = Colors.LightBeige
    )
}

@Composable
fun DetailInformation(info: String) {

    Text(
        text = info,
        style = MaterialTheme.typography.bodyLarge,
        color = Colors.LightBeige
    )
}

@Composable
fun DetailRow(title: String, information: String) {

    Row(modifier = Modifier.padding(start = 25.dp, top = 4.dp)) {
        DetailHeading(title = title)
        Spacer(modifier = Modifier.width(10.dp))
        DetailInformation(info = information)
    }
}
@Composable
fun Reviews(title: String, review: List<Review>){
    Row(modifier = Modifier.padding(start = 25.dp, top = 4.dp)) {
        DetailHeading(title = title)
        Spacer(modifier = Modifier.width(10.dp))
        /*
        LazyColumn{
            items(review){ item ->
                DetailInformation(info = "${item.author} : ")
                DetailInformation(info = item.content)
            }
        }

         */
    }
}
@Composable
fun DetailRowForListsData(title: String, informationList: List<String>){

    Row(modifier = Modifier.padding(start = 25.dp, top = 4.dp)) {
        DetailHeading(title = title)
        Spacer(modifier = Modifier.width(10.dp))
        LazyRow{
            items(informationList){ item ->
                DetailInformation(info = item)
                Text(text = " / ",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    )
            }
        }
    }

}

val sampleMediaDetails = MediaDetails(
    id = 45789,
    name = "Sturm der Liebe",
    posterPath = "/9oZjOh3Va3FsiLGouhSogFsBX9G.jpg",
    genres = listOf(Genre( "Action"), Genre( "Adventure")),
    homepage = "https://www.youtube.com/",
    releaseDate = "2022-01-01",
    spokenLanguages = listOf(Language("English")),
    voteCount = 1000,
    voteAverage = 8.5,
    reviews = listOf(Review("Author1", "This is a great movie!"),Review("Author2", "This is a great movie!ffffdgzqhfdzhsdhfsndhyt nbgdb sgf db")),
    numberOfEpisodes = 4254,
    numberOfSeasons = 20,
    episodeRunTime = null,
    nextEpisodeToAir = NextEpisode(
        name = "Episode 91",
        airDate = "2024-04-18",
        episodeNumber = 91,
        seasonNumber = 20
    ),
    runtime = 120,
    status = "Released"
)


@Preview(showBackground = true)
@Composable
fun PreviewMediaDetailContent() {
    val navController = rememberNavController()
    MediaDetailContent(details = sampleMediaDetails, navController = navController)
}

