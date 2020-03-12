package com.sun_asterisk.moviedb_50.data.model

import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

data class Casts(
    val castId: Int,
    val castName: String,
    val castProfilePath: String
) {
    constructor(response: MovieDetailsResponse.Casts) : this(
        castId=response.castId,
        castName = response.castName,
        castProfilePath = response.castProfilePath
    )
}
