package com.sun_asterisk.moviedb_50.data.model

import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

data class Genres(
    val genresID: Int,
    val genresName: String
) {
    constructor(response: GenresResponse.Data) : this(
        genresID = response.genresID,
        genresName = response.genresName
    )

    constructor(response: MovieDetailsResponse.Genres) : this(
        genresID = response.genresID,
        genresName = response.genresName
    )
}
