package com.sun_asterisk.moviedb_50.data.model

data class Genres(
    val genresID: Int,
    val genresName: String
) {
    constructor(response: GenresResponse.Data) : this(
        genresID = response.genresID,
        genresName = response.genresName
    )
}
