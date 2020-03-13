package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class MovieTrailer(
    val movieTrailerID: String,
    val movieTrailerKey: String,
    val movieTrailerName: String
) {
    constructor(jsonObject: JSONObject) : this(
        movieTrailerID = jsonObject.optString(MovieTrailerEntry.MOVIE_TRAILER_ID),
        movieTrailerKey = jsonObject.optString(MovieTrailerEntry.MOVIE_TRAILER_KEY),
        movieTrailerName = jsonObject.optString(MovieTrailerEntry.MOVIE_TRAILER_NAME)
    )

    object MovieTrailerEntry {
        const val MOVIE_TRAILER_VIDEO = "videos"
        const val MOVIE_TRAILER_RESULTS = "results"
        const val MOVIE_TRAILER_ID = "id"
        const val MOVIE_TRAILER_KEY = "key"
        const val MOVIE_TRAILER_NAME = "name"
    }
}
