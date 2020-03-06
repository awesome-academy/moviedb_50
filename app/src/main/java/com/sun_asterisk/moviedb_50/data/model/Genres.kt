package com.sun_asterisk.moviedb_50.data.model


data class Genres(
    val genresID: Int,
    val genresName: String
) {
    object GenresEntry {
        const val GENRES_LIST = "genres"
        const val GENRES = "genre_ids"
        const val GENRES_ID = "id"
        const val GENRES_NAME = "name"
    }
}
