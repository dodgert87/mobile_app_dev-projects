package com.example.movierecommendation.api

import android.util.Log
import com.example.movierecommendation.dataclass.Country
import com.example.movierecommendation.dataclass.Item
import com.example.movierecommendation.dataclass.MediaDetails
import com.example.movierecommendation.dataclass.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Path


data class GetResponse(
    val results: List<Item>
)

data class GetReviewsResponse(
    val results: List<Review>
)

interface TmdbApi {
    @Headers("Accept: application/json")
    @GET("configuration/countries")
    fun getCountries(
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): Call<List<Country>>
    
    @GET("trending/all/week")
    fun getTrending(
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String

    ): Call<GetResponse>

    @GET("movie/popular")
    fun getTopMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("movie/now_playing")
    fun getNewMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String? = null,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): Call<MediaDetails>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): Call<GetReviewsResponse>

    @GET("tv/popular")
    fun getTopTv(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("tv/airing_today")
    fun getNewTv(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("timezone") timeZone: String? = null,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("tv/on_the_air")
    fun getUpcomingTv(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("timezone") timeZone: String? = null,
        @Query("api_key") apiKey: String
    ): Call<GetResponse>

    @GET("tv/{series_id}")
    fun getTvDetails(
        @Path("series_id") seriesId: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): Call<MediaDetails>

    @GET("tv/{series_id}/reviews")
    fun getTvReviews(
        @Path("series_id") seriesId: Int,
        @Query("language") language: String = "en-US",
        @Query("api_key") apiKey: String
    ): Call<GetReviewsResponse>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    internal const val API_KEY = "2d8b23bb65a646faa015ef8971c032f2"


    val instance: TmdbApi by lazy {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TmdbApi::class.java)
    }
}

class TmdbRequest(private val api: TmdbApi = RetrofitClient.instance) {


    fun fetchCountries(success: (List<Country>) -> Unit, failure: (Throwable) -> Unit) {
        api.getCountries(apiKey = RetrofitClient.API_KEY).enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if (response.isSuccessful) {

                    Log.d("APIConfigResponse", "Countries fetched successfully: ${response.body()}")
                    val countries = response.body() ?: emptyList()
                    success(countries)
                } else {
                    Log.e(
                        "APIConfigResponse",
                        "Failed with HTTP status: ${response.code()} and message: ${response.message()}"
                    )
                    response.errorBody()?.let {
                        Log.e("APIConfigResponse", "Error response body: ${it.string()}")
                    }
                    failure(RuntimeException("Failed to fetch countries: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                // Log the exception
                Log.e("APIConfigResponse", "Network call failed with exception: ${t.message}")
                failure(t)
            }
        })
    }

    fun fetchTrending(success: (List<Item>) -> Unit, failure: (Throwable) -> Unit) {

        api.getTrending(apiKey = RetrofitClient.API_KEY).enqueue(object : Callback<GetResponse> {
            override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                if (response.isSuccessful) {
                    Log.d("APIResponse", "API call successful: ${response.body()}")
                    val items = response.body()?.results ?: emptyList()
                    success(items)
                } else {
                    Log.e(
                        "APIResponse",
                        "Failed with HTTP status: ${response.code()} and message: ${response.message()}"
                    )
                    if (response.errorBody() != null) {
                        Log.e(
                            "APIResponse",
                            "Error response body: ${response.errorBody()?.string()}"
                        )
                    }
                    failure(RuntimeException("Failed to get trending movies: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun fetchTopMovies(countryIsoCode: String? = null, success: (List<Item>) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getTopMovies(region = countryIsoCode, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<GetResponse> {
                override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                    if (response.isSuccessful) {
                        Log.d("APIResponse", "Top Movies fetched successfully: ${response.body()}")
                        val movies = response.body()?.results ?: emptyList()
                        movies.forEach { item ->
                            item.mediaType = "movie"
                        }
                        success(movies)
                    } else {
                        failure(Exception("Failed to fetch top movies: ${response.message()}"))
                        Log.e(
                            "APIResponse",
                            "Failed with HTTP status: ${response.code()} and message: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

    fun fetchNewMovies(
        countryIsoCode: String? = null, success: (List<Item>) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getNewMovies(region = countryIsoCode, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<GetResponse> {
                override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                    if (response.isSuccessful) {
                        Log.d("APIResponse", "New Movies fetched successfully: ${response.body()}")
                        val movies = response.body()?.results ?: emptyList()
                        movies.forEach { item ->
                            item.mediaType = "movie"
                        }
                        success(movies)
                    } else {
                        failure(Exception("Failed to fetch top movies: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

    fun fetchUpcomingMovies(
        countryIsoCode: String? = null, success: (List<Item>) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getUpcomingMovies(region = countryIsoCode, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<GetResponse> {
                override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                    if (response.isSuccessful) {

                        Log.d(
                            "APIResponse",
                            "Upcoming Movies fetched successfully: ${response.body()}"
                        )
                        val movies = response.body()?.results ?: emptyList()
                        movies.forEach { item ->
                            item.mediaType = "movie"
                        }
                        success(movies)
                    } else {
                        failure(Exception("Failed to fetch Upcoming movies: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

    fun fetchTopTv(success: (List<Item>) -> Unit, failure: (Throwable) -> Unit) {
        api.getTopTv(apiKey = RetrofitClient.API_KEY).enqueue(object : Callback<GetResponse> {

            override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                if (response.isSuccessful) {

                    Log.d("APIResponse", "Top Tv fetched successfully: ${response.body()}")
                    val tv = response.body()?.results ?: emptyList()
                    tv.forEach { item ->
                        item.mediaType = "tv"
                    }
                    success(tv)
                } else {
                    failure(Exception("Failed to fetch Top Tv: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                failure(t)
            }
        })
    }

    fun fetchNewTv(countryTimeZone: String? = null, success: (List<Item>) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getNewTv(timeZone = countryTimeZone, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<GetResponse> {

                override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                    if (response.isSuccessful) {

                        Log.d("APIResponse", "New Tv fetched successfully: ${response.body()}")
                        val tv = response.body()?.results ?: emptyList()
                        tv.forEach { item ->
                            item.mediaType = "tv"
                        }
                        success(tv)
                    } else {

                        failure(Exception("Failed to fetch New Tv: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

    fun fetchUpcomingTv(countryTimeZone: String? = null, success: (List<Item>) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getUpcomingTv(timeZone = countryTimeZone, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<GetResponse> {

                override fun onResponse(call: Call<GetResponse>, response: Response<GetResponse>) {
                    if (response.isSuccessful) {

                        Log.d("APIResponse", "Upcoming Tv fetched successfully: ${response.body()}")
                        val tv = response.body()?.results ?: emptyList()
                        tv.forEach { item ->
                            item.mediaType = "tv"
                        }
                        success(tv)
                    } else {

                        failure(Exception("Failed to fetch Upcoming Tv: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<GetResponse>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

    fun fetchTvDetails(tvId: Int, success: (MediaDetails) -> Unit, failure: (Throwable) -> Unit) {
        api.getTvDetails(seriesId = tvId, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<MediaDetails> {

                override fun onResponse(
                    call: Call<MediaDetails>,
                    response: Response<MediaDetails>
                ) {
                    if (response.isSuccessful) {

                        Log.d("APIResponse", "Tv details fetched successfully: ${response.body()}")
                        val tvDetails = response.body()
                        if (tvDetails != null) {
                            api.getTvReviews(seriesId = tvId, apiKey = RetrofitClient.API_KEY).enqueue(object : Callback<GetReviewsResponse> {
                                override fun onResponse(
                                    call: Call<GetReviewsResponse>,
                                    response: Response<GetReviewsResponse>
                                ) {
                                   if(response.isSuccessful){
                                       tvDetails.reviews = response.body()?.results ?: emptyList()
                                       success(tvDetails)
                                   } else {
                                       failure(Exception("Failed to fetch reviews: ${response.message()}"))
                                   }
                                }

                                override fun onFailure(
                                    call: Call<GetReviewsResponse>,
                                    t: Throwable
                                ) { failure(t) }
                            })
                        } else {
                            failure(Exception("No Tv details found"))
                        }
                    } else {

                        failure(Exception("Failed to fetch Upcoming Tv: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<MediaDetails>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }


    fun fetchMovieDetails(movieId: Int, success: (MediaDetails) -> Unit, failure: (Throwable) -> Unit
    ) {
        api.getMovieDetails(movieId = movieId, apiKey = RetrofitClient.API_KEY)
            .enqueue(object : Callback<MediaDetails> {
                override fun onResponse(
                    call: Call<MediaDetails>,
                    response: Response<MediaDetails>
                ) {
                    if (response.isSuccessful) {
                        val movieDetails = response.body()
                        if (movieDetails != null) {
                            api.getMovieReviews(movieId = movieId, apiKey = RetrofitClient.API_KEY)
                                .enqueue(object : Callback<GetReviewsResponse> {
                                    override fun onResponse(
                                        call: Call<GetReviewsResponse>,
                                        response: Response<GetReviewsResponse>
                                    ) {
                                        if (response.isSuccessful) {
                                            movieDetails.reviews = response.body()?.results ?: emptyList()
                                            success(movieDetails)
                                        } else {
                                            failure(Exception("Failed to fetch reviews: ${response.message()}"))
                                        }
                                    }

                                    override fun onFailure(call: Call<GetReviewsResponse>, t: Throwable) {
                                        failure(t)
                                    }
                                })
                        } else {
                            failure(Exception("No movie details found"))
                        }
                    } else {


                        failure(Exception("Failed to fetch movie details: ${response.message()}"))
                    }
                }

                override fun onFailure(call: Call<MediaDetails>, t: Throwable) {
                    Log.e("APIResponse", "Network call failed with exception: ${t.message}")
                    failure(t)
                }
            })
    }

}





