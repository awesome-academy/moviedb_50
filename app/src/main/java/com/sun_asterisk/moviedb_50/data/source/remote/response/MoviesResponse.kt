package com.sun_asterisk.moviedb_50.data.source.remote.response

import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.ParseDataToObject
import org.json.JSONObject

data class MoviesResponse(var list: List<Movie>) {
    constructor(jsonObject: JSONObject) : this(
        list = ParseDataToObject().parJsonToMovies(jsonObject)
    )
}
