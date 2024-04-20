package com.example.movierecommendation.dataclass

import com.google.gson.annotations.SerializedName

data class MediaDetails(
    val id: Int,

    @SerializedName("title", alternate = ["name"])
    val name: String,

    @SerializedName("poster_path")
    val posterPath: String?,

    val genres: List<Genre>,

    val homepage: String?,

    @SerializedName("release_date", alternate = ["first_air_date"])
    val releaseDate: String?,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,

    @SerializedName("vote_count")
    val voteCount: Int,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("Review")
    var reviews: List<Review> = listOf(),

    // TV Show Specific Fields
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,

    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,

    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int>? = null,

    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: NextEpisode? = null,

    // Movie Specific Fields
    val runtime: Int? = null,
    val status: String? = null
){
    val genresName: List<String>
        get() = genres.map {it.name}

    val languageList: List<String>
        get() = spokenLanguages.map { it.englishName }
}


data class Genre(
    val name: String
)

data class Language(
    @SerializedName("english_name")
    val englishName: String
)

data class Review(
    val author: String,
    val content: String
)

data class NextEpisode(

    @SerializedName("air_date")
    val airDate: String,

    @SerializedName("episode_number")
    val episodeNumber: Int,

    @SerializedName("season_number")
    val seasonNumber: Int
)
