package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse
import org.json.JSONObject

class MoviesResponseHandler : ParseDataWithJson<MoviesResponse> {
    override fun parseToObject(jsonData: String): MoviesResponse =
        MoviesResponse(JSONObject(jsonData))
}
