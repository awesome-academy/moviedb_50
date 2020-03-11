package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONException
import org.json.JSONObject

data class MoviesResponse(
    var list: List<Data>
) {
    constructor(jsonObject: JSONObject) : this(
        list = mutableListOf<Data>().apply {
            try {
                val jsonArray = jsonObject.getJSONArray(MovieEntry.MOVIE)
                for (i in 0 until jsonArray.length()) {
                    add(Data(jsonArray.getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    )

    data class Data(
        val movieID: Int,
        val movieTitle: String,
        val moviePosterPath: String,
        val movieBackdropPath: String,
        val movieVoteAverage: Double
    ) {
        constructor(movieJson: JSONObject) : this(
            movieID = movieJson.optInt(MovieEntry.ID),
            movieTitle = movieJson.optString(MovieEntry.TITLE),
            moviePosterPath = movieJson.optString(MovieEntry.POSTER_PATH),
            movieBackdropPath = movieJson.optString(MovieEntry.BACKDROP_PATH),
            movieVoteAverage = movieJson.optDouble(MovieEntry.VOTE_AVERAGE)
        )
    }

    object MovieEntry {
        const val MOVIE = "results"
        const val ID = "id"
        const val TITLE = "title"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val VOTE_AVERAGE = "vote_average"
    }
}
