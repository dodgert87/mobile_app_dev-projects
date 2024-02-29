package com.example.weatherapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


data class RawData(
    var dt: Long,
    var main: Main,
    var wind: Wind,
    var name: String,
    var weather: List<Weather>,
    var day: String,
    var windDirection: Int
)

data class Main(
    var temp: Double,
)

data class Weather(

    var main: String
)

data class Wind(
    var speed: Double,
    var deg: Int
)

interface  ApiService {
    @GET("weather?q=tampere&units=metric&appid=8cf209add304217bfa67efc758688c7b")
    suspend fun fetchWeatherData(): RawData
}

object RetrofitInstance {

    private const val weatherBase_URL = "https://api.openweathermap.org/data/2.5/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(weatherBase_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

@Composable

fun fetchData(): RawData? {


    var weatherItem by remember { mutableStateOf<RawData?>(null)}
    var isLoading by remember { mutableStateOf(true)}


    LaunchedEffect(Unit){
        weatherItem = try {
            RetrofitInstance.apiService.fetchWeatherData().also {
                Log.d("fetchData", "API Call Success: $it")
            }
        } catch (e: Exception) {
            Log.e("fetchData", "API Call Failed", e)
            null
        }
        isLoading = false
    }
    //Log.d("weatheritem", "Temperature retrieved successfully: $weatherItem")

    if (!isLoading && weatherItem != null){

        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(weatherItem!!.dt ),ZoneId.systemDefault())
        weatherItem!!.day = dateTime.dayOfWeek.toString().lowercase()
        val windDirection = when {
            weatherItem!!.wind.deg >= 337.5 ||  weatherItem!!.wind.deg < 22.5 -> R.string.north
            weatherItem!!.wind.deg >= 22.5 &&  weatherItem!!.wind.deg < 67.5 -> R.string.Northeast
            weatherItem!!.wind.deg >= 67.5 &&  weatherItem!!.wind.deg < 112.5 -> R.string.East
            weatherItem!!.wind.deg >= 157.5 &&  weatherItem!!.wind.deg < 202.5 -> R.string.South
            weatherItem!!.wind.deg >= 202.5 &&  weatherItem!!.wind.deg < 247.5 -> R.string.Southwest
            weatherItem!!.wind.deg >= 247.5 &&  weatherItem!!.wind.deg < 292.5 -> R.string.West
            weatherItem!!.wind.deg >= 292.5 &&  weatherItem!!.wind.deg < 337.5 -> R.string.Northwest
            else -> 0
        }
        weatherItem!!.windDirection = windDirection;

        return weatherItem
    }
    return null
}

