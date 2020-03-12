package com.sun_asterisk.moviedb_50.data.model

import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

data class Produces(
    val produceID: Int,
    val produceLogo: String,
    val produceName: String,
    val produceCountry: String
) {
    constructor(response: MovieDetailsResponse.Produces) : this(
        produceID = response.produceID,
        produceLogo = response.produceLogo,
        produceName = response.produceName,
        produceCountry = response.produceCountry
    )
}
