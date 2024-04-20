package com.example.movierecommendation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movierecommendation.component.MainScreen
import com.example.movierecommendation.component.MediaDetailScreen
import com.example.movierecommendation.dataclass.MEDIA_DETAIL_ARGUMENT_KEY
import com.example.movierecommendation.dataclass.Screen


@Composable
    fun SetupNavGraph(
        navController: NavHostController,
        movieViewModel: MovieViewModel
    ){
       NavHost(
           navController = navController,
           startDestination = Screen.Home.route
       ){
           composable(
               route = Screen.Home.route
           ){
               MainScreen(movieViewModel ,navController = navController )
           }
           composable(
               route = Screen.MediaDetail.route,
               arguments = listOf(navArgument(MEDIA_DETAIL_ARGUMENT_KEY){
                   type = NavType.StringType
               })
           ){
               MediaDetailScreen(item = it.arguments?.getString(MEDIA_DETAIL_ARGUMENT_KEY)!!, navController = navController)
           }
       }
    }
