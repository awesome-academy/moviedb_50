package com.sun_asterisk.moviedb_50.data.source.remote.response

import com.sun_asterisk.moviedb_50.data.model.*
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.ParseDataToObject
import org.json.JSONObject

data class MovieDetailsResponse(
    var movies: Movie,
    var genres: List<Genres>,
    var produce: List<Produce>,
    var casts: List<Cast>,
    var trailers: List<MovieTrailer>
) {
    constructor(jsonObject: JSONObject) : this(
        movies = Movie(jsonObject),
        genres = ParseDataToObject.parJsonToGenres(jsonObject),
        produce = ParseDataToObject.parJsonToProduce(jsonObject),
        casts = ParseDataToObject.parJsonToCasts(jsonObject),
        trailers = ParseDataToObject.parJsonToTrailer(jsonObject)
    )
}
