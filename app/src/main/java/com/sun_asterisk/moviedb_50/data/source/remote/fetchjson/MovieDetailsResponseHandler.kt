package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
import org.json.JSONObject

class MovieDetailsResponseHandler : ParseDataWithJson<MovieDetailsResponse> {
    override fun parseToObject(jsonData: String): MovieDetailsResponse =
        MovieDetailsResponse(JSONObject(jsonData))
}
