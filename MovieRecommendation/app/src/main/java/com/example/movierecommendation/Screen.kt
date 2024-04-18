package com.example.movierecommendation
const val MEDIA_DETAIL_ARGUMENT_KEY = "mediaId"
sealed class Screen(val route: String){

    object Home: Screen(route = "home_screen")
    object MediaDetail: Screen(route = "media_detail_screen/{$MEDIA_DETAIL_ARGUMENT_KEY}")

}