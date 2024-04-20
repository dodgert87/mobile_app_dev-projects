package com.example.movierecommendation.component

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movierecommendation.R
import com.example.movierecommendation.ui.theme.Colors
import com.example.movierecommendation.api.TmdbRequest
import com.example.movierecommendation.dataclass.MediaDetails
import com.example.movierecommendation.dataclass.Review
import com.example.movierecommendation.dataclass.Screen
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun MediaDetailScreen(item: String, navController: NavController) {
    val parsedValues = item.split(',')
    val listState = rememberLazyListState()

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

    MediaDetailContent(details = detailsState.value, navController, listState)
}

@Composable
fun DetailSection(details: MediaDetails, lazyListState: LazyListState) {
    val placeHolder = Review(stringResource(R.string.no_available_review), " ")
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
            ),
        state = lazyListState
    ) {
        item {
            Text(
                text = details.name,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Medium),
                color = Colors.cyan,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
        item {
            DetailRow(stringResource(R.string.release_date), details.releaseDate.toString())
        }
        if (details.episodeRunTime != null) {
            if (details.episodeRunTime.isNotEmpty()) {
                item {
                    DetailRow(
                        title = stringResource(R.string.run_time),
                        information = "${details.episodeRunTime.first()} min"
                    )
                }
            }
        } else if (details.runtime != null) {
            item {
                DetailRow(title = stringResource(R.string.run_time),
                    information = "${details.runtime} min")
            }
        }

        item {
            DetailRow(
                title = stringResource(R.string.rating),
                information = BigDecimal(details.voteAverage).setScale(1, RoundingMode.HALF_UP)
                    .toString()
            )
        }

        item { DetailRow(title = stringResource(R.string.number_of_votes), information = details.voteCount.toString()) }
        if (details.numberOfSeasons != null && details.numberOfEpisodes != null) {
            item {
                DetailRow(
                    title = stringResource(R.string.seasons),
                    information = details.numberOfSeasons.toString()
                )
            }
            item {
                DetailRow(
                    title = stringResource(R.string.episodes),
                    information = details.numberOfEpisodes.toString()
                )
            }
        }

        item { DetailRowForListsData(title = stringResource(R.string.genres), details.genresName) }

        item {
            DetailRowForListsData(
                title = stringResource(R.string.language),
                informationList = details.languageList
            )
        }
        if (details.nextEpisodeToAir != null) {

            item {
                DetailRow(
                    title = stringResource(R.string.next_episode),
                    information = details.nextEpisodeToAir.episodeNumber.toString()
                )
            }
            item { DetailRow(title = stringResource(R.string.on_the), information = details.nextEpisodeToAir.airDate) }
        }
        if (details.reviews.isNotEmpty()) {
            item { DetailRow(title = stringResource(R.string.reviews), information = "")
            Spacer(modifier = Modifier.height(10.dp))}
            items(details.reviews) { review ->
                Review(review)
            }
        } else {
            item { DetailRow(title = stringResource(R.string.reviews), information = "")
                Spacer(modifier = Modifier.height(10.dp))}
            item { Review(review = placeHolder) }
        }
    }
}

@Composable
fun MediaDetailContent(
    details: MediaDetails?,
    navController: NavController,
    lazyListState: LazyListState
) {
    if (details != null) {
        Column {
            ImageSection(
                imageUrl = details.posterPath,
                mediaUrl = details.homepage,
                navController,
                lazyListState
            )
            DetailSection(details = details, lazyListState)
        }
    } else {
        Text(stringResource(R.string.loading_details))
    }
}

@Composable
fun getImageSize(lazyListState: LazyListState): Dp {
    val isScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 0
        }
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val initialImageSize = screenHeight * 0.75f
    val scrolledImageSize = screenHeight * 0.3f
    return if (isScrolled) scrolledImageSize else initialImageSize
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ImageSection(
    imageUrl: String?,
    mediaUrl: String?,
    navController: NavController,
    lazyListState: LazyListState
) {
    val context = LocalContext.current

    Box {
        imageUrl?.let {
            val painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500$imageUrl")
            val imageSize = getImageSize(lazyListState)

            val animatedImageSize = animateDpAsState(targetValue = imageSize, label = "")
            Image(
                painter = painter,
                contentDescription = "Media Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(animatedImageSize.value),
                contentScale = ContentScale.Crop
            )

            BackButton(
                navController = navController,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            )
            if (mediaUrl?.length!! > 1) {
                MyWatchNowButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 18.dp, end = 10.dp),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mediaUrl))
                        context.startActivity(intent)
                    }
                )
            }
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
fun MyWatchNowButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(Color.Black.copy(alpha = 0.5f)),

        content = {
            Text(
                stringResource(R.string.watch_now),
                color = Color.White,
            )
        }
    )
}

@Composable
fun DetailRow(title: String, information: String) {

    Row(modifier = Modifier.padding(start = 25.dp, top = 13.dp)) {
        DetailHeading(title = title)
        Spacer(modifier = Modifier.width(13.dp))
        DetailInformation(info = information)
    }
}

@Composable
fun DetailHeading(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
        color = Colors.LightBeige
    )
}

@Composable
fun DetailInformation(info: String) {
    Text(
        text = info,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Light),
        color = Colors.LightBeige
    )
}

@Composable
fun DetailRowForListsData(title: String, informationList: List<String>) {

    Row(modifier = Modifier.padding(start = 25.dp, top = 13.dp)) {
        DetailHeading(title = title)
        Spacer(modifier = Modifier.width(10.dp))
        LazyRow {
            items(informationList) { item ->
                DetailInformation(info = item)
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = " / ",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }

}

@Composable
fun Review(review: Review) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFc3c3e6),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = review.author,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = review.content,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
        }
    }
}


