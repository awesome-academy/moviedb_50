package com.sun_asterisk.moviedb_50.data.model

import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse

data class Movie(
    val movieID: Int,
    val movieTitle: String,
    val moviePosterPath: String,
    val movieBackdropPath: String,
    val movieVoteAverage: Double
) {
    constructor(response: MoviesResponse.Data) : this(
        movieID = response.movieID,
        movieTitle = response.movieTitle,
        moviePosterPath = response.moviePosterPath,
        movieBackdropPath = response.movieBackdropPath,
        movieVoteAverage = response.movieVoteAverage
    )
}
