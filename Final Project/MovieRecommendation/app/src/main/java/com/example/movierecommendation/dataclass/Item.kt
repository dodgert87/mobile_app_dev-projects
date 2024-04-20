package com.example.movierecommendation.dataclass

import com.google.gson.annotations.SerializedName

data class Item(
    val id: Int,
    private val name: String? = null,
    private val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("release_date")
    private var effectiveReleaseDate: String? = null,

    @SerializedName("first_air_date")
    private val firstAirDate: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("media_type")
    var mediaType: String
) {
    val mediaName: String?
        get() = if (mediaType == "movie") title else name

    val releaseDate: String?
        get() = if (mediaType == "tv") firstAirDate else effectiveReleaseDate


    val shortenMediaName: String
        get() = if (mediaName != null && mediaName!!.length > 12) {
            mediaName!!.substring(0, 12) + "..."
        } else {
            mediaName ?: "Unknown"
        }
}
