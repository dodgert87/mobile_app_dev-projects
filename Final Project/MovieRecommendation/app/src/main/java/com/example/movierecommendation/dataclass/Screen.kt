package com.example.movierecommendation.dataclass
const val MEDIA_DETAIL_ARGUMENT_KEY = "mediaId"
sealed class Screen(val route: String){

    data object Home: Screen(route = "home_screen")
    data object MediaDetail: Screen(route = "media_detail_screen/{$MEDIA_DETAIL_ARGUMENT_KEY}")

}