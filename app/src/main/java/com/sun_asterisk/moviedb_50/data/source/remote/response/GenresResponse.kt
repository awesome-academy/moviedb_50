package com.sun_asterisk.moviedb_50.data.source.remote.response

import org.json.JSONException
import org.json.JSONObject

data class GenresResponse(var list: List<Data>) {
    constructor(jsonObject: JSONObject) : this(
        list = mutableListOf<Data>().apply {
            try {
                val jsonArray = jsonObject.getJSONArray(GenresEntry.GENRES_LIST)
                for (i in 0 until jsonArray.length()) {
                    add(Data(jsonArray.getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    )

    data class Data(
        val genresID: Int,
        val genresName: String
    ) {
        constructor(genresJson: JSONObject) : this(
            genresID = genresJson.optInt(GenresEntry.GENRES_ID),
            genresName = genresJson.optString(GenresEntry.GENRES_NAME)
        )
    }

    object GenresEntry {
        const val GENRES_LIST = "genres"
        const val GENRES_ID = "id"
        const val GENRES_NAME = "name"
    }
}
