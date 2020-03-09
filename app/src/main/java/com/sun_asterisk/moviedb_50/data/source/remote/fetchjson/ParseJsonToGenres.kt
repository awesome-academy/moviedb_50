package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.model.Genres
import org.json.JSONException
import org.json.JSONObject

class ParseJsonToGenres {
    fun parseJsonToData(jsonObject: JSONObject): MutableList<Genres>? {
        val genresList = mutableListOf<Genres>()
        try {
            val jsonArray = jsonObject.getJSONArray(Genres.GenresEntry.GENRES_LIST)
            for (i in 0 until jsonArray.length()) {
                val genresJson = jsonArray.getJSONObject(i)
                val genres: Genres? = genresJson?.let { parseJsonToObject(it) }
                genres?.let { genresList.add(it) }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return genresList
    }

    private fun parseJsonToObject(movieJson: JSONObject?): Genres? {
        try {
            val id = movieJson?.optInt(Genres.GenresEntry.GENRES_ID)
            val name = movieJson?.optString(Genres.GenresEntry.GENRES_NAME)
            return Genres(id, name)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
