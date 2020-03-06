package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.model.Genres
import org.json.JSONException
import org.json.JSONObject

class ParseJsonToGenres {
    fun parseJsonToData(jsonObject: JSONObject): MutableList<Genres>? {
        val genresList: MutableList<Genres>? = mutableListOf()
        try {
            val jsonArray = jsonObject.getJSONArray(Genres.GenresEntry.GENRES_LIST)
            for (i in 0 until jsonArray.length()) {
                val genresJson = jsonArray.getJSONObject(i)
                val genres: Genres = parseJsonToObject(
                    genresJson
                )
                genresList?.add(genres)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return genresList

    }

    private fun parseJsonToObject(movieJson: JSONObject?): Genres {
        var id = 0
        var name = ""

        try {
            id = movieJson!!.optInt(Genres.GenresEntry.GENRES_ID)
            name = movieJson.optString(Genres.GenresEntry.GENRES_NAME)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Genres(id, name)
    }
}
