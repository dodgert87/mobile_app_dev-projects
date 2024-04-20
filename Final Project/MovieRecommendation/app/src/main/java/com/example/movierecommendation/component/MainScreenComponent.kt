package com.example.movierecommendation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.movierecommendation.MovieViewModel
import com.example.movierecommendation.R
import com.example.movierecommendation.component.RegionAndTimezoneDropdownMenu
import com.example.movierecommendation.dataclass.Country


@Composable
fun MainScreen(movieViewModel: MovieViewModel, navController: NavController) {
    val trending by movieViewModel.trendingList.observeAsState(initial = emptyList())
    val topMovies by movieViewModel.topMovies.observeAsState(initial = emptyList())
    val newMovies by movieViewModel.newMovies.observeAsState(initial = emptyList())
    val upcomingMovies by movieViewModel.upcomingMovies.observeAsState(initial = emptyList())
    val topTv by movieViewModel.topTv.observeAsState(initial = emptyList())
    val newTv by movieViewModel.newTv.observeAsState(initial = emptyList())
    val upcomingTv by movieViewModel.upcomingTv.observeAsState(initial = emptyList())
    val countries by movieViewModel.countries.observeAsState(listOf())
    val selectedRegionText by movieViewModel.selectedRegionText.observeAsState("Select A Region")
    val autoDetectedCountryIso by movieViewModel.autoDetectedCountryIso.observeAsState()
    val listState = rememberLazyListState()
    var regionName: String = selectedRegionText

    LaunchedEffect(true) {
        listState.scrollToItem(
            index = movieViewModel.scrollIndex.value ?: 0,
            scrollOffset = movieViewModel.scrollOffset.value ?: 0
        )
    }

    LaunchedEffect(Unit) {
        movieViewModel.fetchConfig()
    }

    LaunchedEffect(countries) {

        if (movieViewModel.autoDetectedCountryIso.value != "Null") {
            val countrySearch =
                movieViewModel.countries.value?.find { it.isoCode == autoDetectedCountryIso }
            if (countrySearch != null) {
                movieViewModel.setSelectedRegion(
                    isoCode = countrySearch.isoCode,
                    englishName = countrySearch.englishName
                )
                movieViewModel.setAutoDetectedCountryIso("Null")
                regionName = countrySearch.englishName

            }
        }
    }
    Box {

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF04051c), // Starting color
                            Color(0xFF111270)  // Ending color
                        )
                    )
                )
        ) {
            item {
                RegionAndTimezoneDropdownMenu(
                    regions = listOf(
                        Country(
                            "ALL",
                            "All regions",
                            "All regions"
                        )
                    ) + countries.sortedBy { it.englishName },
                    selectedRegion = regionName,
                    onRegionSelected = { isoCode, englishName ->
                        movieViewModel.setSelectedRegion(
                            isoCode = isoCode,
                            englishName = englishName
                        )
                    }
                )

            }
            if (trending.isNotEmpty()) {
                item { CategoryRow(title = stringResource(R.string.trending_movies), items = trending, navController) }
            }
            if (topMovies.isNotEmpty()) {
                item { CategoryRow(title = stringResource(R.string.top_movies), items = topMovies, navController) }
            }
            if (newMovies.isNotEmpty()) {
                item { CategoryRow(title = stringResource(R.string.new_movies), items = newMovies, navController) }
            }
            if (upcomingMovies.isNotEmpty()) {
                item {
                    CategoryRow(
                        title = stringResource(R.string.upcoming_movies),
                        items = upcomingMovies,
                        navController
                    )
                }
            }
            if (topTv.isNotEmpty()) {
                item { CategoryRow(title = stringResource(R.string.top_tv_series), items = topTv, navController) }
            }
            if (newTv.isNotEmpty()) {
                item { CategoryRow(title = stringResource(R.string.new_tv_series), items = newTv, navController) }
            }
            if (upcomingTv.isNotEmpty()) {
                item {
                    CategoryRow(
                        title = stringResource(R.string.upcoming_tv_series),
                        items = upcomingTv,
                        navController
                    )
                }
            }
        }
    }



    LaunchedEffect(listState) {
        snapshotFlow {
            Pair(
                listState.firstVisibleItemIndex,
                listState.firstVisibleItemScrollOffset
            )
        }
            .collect { (index, offset) ->
                movieViewModel.updateScrollIndex(index)
                movieViewModel.updateScrollOffset(offset)
            }
    }
}
