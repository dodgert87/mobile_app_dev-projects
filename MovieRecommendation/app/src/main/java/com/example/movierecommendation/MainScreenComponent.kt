package com.example.movierecommendation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.NavController


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


    LazyColumn(state = listState) {

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
                    movieViewModel.setSelectedRegion(isoCode = isoCode, englishName = englishName)
                }
            )

        }
        if (trending.isNotEmpty()) {
            item { CategoryRow(title = "Trending Movies", items = trending, navController) }
        }
        if (topMovies.isNotEmpty()) {
            item { CategoryRow(title = "Top Movies", items = topMovies, navController) }
        }
        if (newMovies.isNotEmpty()) {
            item { CategoryRow(title = "New Movies", items = newMovies, navController) }
        }
        if (upcomingMovies.isNotEmpty()) {
            item { CategoryRow(title = "Upcoming Movies", items = upcomingMovies, navController) }
        }
        if (topTv.isNotEmpty()) {
            item { CategoryRow(title = "Top TV Series", items = topTv, navController) }
        }
        if (newTv.isNotEmpty()) {
            item { CategoryRow(title = "New TV Series", items = newTv, navController) }
        }
        if (upcomingTv.isNotEmpty()) {
            item { CategoryRow(title = "Upcoming TV Series", items = upcomingTv, navController) }
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
