package com.example.movierecommendation

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


data class TimeZoneResponse(
    val countryCode: String? = null,
    val country: String? = null,
    val timezone: String? = null
)

interface TimeZoneApi{
    @GET("json/")
    fun getTimeZone(): Call<TimeZoneResponse>
}

object TimeZoneRetrofitClient {
    private const val BASE_URL = "http://ip-api.com/"

    val instance: TimeZoneApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(TimeZoneApi::class.java)
    }
}

class TimeZoneRequest (private  val api:TimeZoneApi = TimeZoneRetrofitClient.instance){

    fun fetchTimeZone(success: (TimeZoneResponse) -> Unit, failure: (Throwable) -> Unit){
        api.getTimeZone().enqueue(object : Callback<TimeZoneResponse>{
            override fun onResponse(
                call: Call<TimeZoneResponse>,
                response: Response<TimeZoneResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("TimeZoneApi", "time zone api fetched successfully: ${response.body()}")
                    val timeZone = response.body()
                    if (timeZone != null) {
                        success(timeZone)
                    }else{
                        Log.e("TimeZoneApi", "Failed with HTTP status: ${response.code()} and message: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<TimeZoneResponse>, t: Throwable) {
                Log.e("TimeZoneApi", "Network call failed with exception: ${t.message}")
                failure(t)
            }
        })

    }
}