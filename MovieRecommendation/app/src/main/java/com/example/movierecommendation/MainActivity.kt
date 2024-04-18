package com.example.movierecommendation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movierecommendation.ui.theme.MovieRecommendationTheme


class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieRecommendationTheme {
                val movieViewModel: MovieViewModel by viewModels()
                movieViewModel.fetchData()

                val context = LocalContext.current
                movieViewModel.fetchConfig()
                TelephonyPermissionHandler(context) { countryCode ->
                    if (countryCode != null) {
                        movieViewModel.setAutoDetectedCountryIso(countryCode)
                        Log.d("Gps","Country code is: $countryCode")
                    } else {
                        Log.d("Gps","Failed to fetch country code.")
                    }
                }

                navController = rememberNavController()
                SetupNavGraph(navController = navController, movieViewModel)
                }
            }
        }
    }

