package com.sun_asterisk.moviedb_50.data.model

import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

data class MovieDetails(
    val movieID: Int,
    val movieTitle: String,
    val movieOverView: String,
    val moviePosterPath: String,
    val movieBackdropPath: String,
    val movieVoteAverage: Double,
    val movieReleaseDate: String
) {
    constructor(detailsResponse: MovieDetailsResponse.Movie) : this(
        movieID = detailsResponse.movieID,
        movieTitle = detailsResponse.movieTitle,
        movieOverView = detailsResponse.movieOverView,
        moviePosterPath = detailsResponse.moviePosterPath,
        movieBackdropPath = detailsResponse.movieBackdropPath,
        movieVoteAverage = detailsResponse.movieVoteAverage,
        movieReleaseDate = detailsResponse.movieReleaseDate
    )
}
