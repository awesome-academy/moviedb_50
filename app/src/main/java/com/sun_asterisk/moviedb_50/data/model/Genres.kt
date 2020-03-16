package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class Genres(
    val genresID: Int,
    val genresName: String
) {
    constructor(genresJson: JSONObject) : this(
        genresID = genresJson.optInt(GenresEntry.GENRES_ID),
        genresName = genresJson.optString(GenresEntry.GENRES_NAME)
    )

    object GenresEntry {
        const val GENRES = "genres"
        const val GENRES_ID = "id"
        const val GENRES_NAME = "name"
    }
}
