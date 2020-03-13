package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class Movie(
    val movieID: Int,
    val movieTitle: String,
    val movieOverView: String,
    val moviePosterPath: String,
    val movieBackdropPath: String,
    val movieVoteAverage: Double,
    val movieReleaseDate: String
) {
    constructor(movieJson: JSONObject) : this(
        movieID = movieJson.optInt(MovieEntry.ID),
        movieTitle = movieJson.optString(MovieEntry.TITLE),
        movieOverView = movieJson.optString(MovieEntry.OVERVIEW),
        moviePosterPath = movieJson.optString(MovieEntry.POSTER_PATH),
        movieBackdropPath = movieJson.optString(MovieEntry.BACKDROP_PATH),
        movieVoteAverage = movieJson.optDouble(MovieEntry.VOTE_AVERAGE),
        movieReleaseDate = movieJson.optString(MovieEntry.RELEASE_DATE)
    )

    object MovieEntry {
        const val MOVIE = "results"
        const val ID = "id"
        const val TITLE = "title"
        const val OVERVIEW = "overview"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val VOTE_AVERAGE = "vote_average"
        const val RELEASE_DATE = "release_date"
    }
}
