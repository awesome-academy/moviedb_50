package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import org.json.JSONObject

class GenresResponseHandler : ParseDataWithJson<GenresResponse> {
    override fun parseToObject(jsonData: String): GenresResponse =
        GenresResponse(JSONObject(jsonData))
}
