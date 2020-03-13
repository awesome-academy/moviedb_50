package com.sun_asterisk.moviedb_50.data.source.remote.response

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.source.remote.fetchjson.ParseDataToObject
import org.json.JSONObject

data class GenresResponse(var list: List<Genres>) {
    constructor(jsonObject: JSONObject) : this(
        list = ParseDataToObject().parJsonToGenres(jsonObject)
    )
}
