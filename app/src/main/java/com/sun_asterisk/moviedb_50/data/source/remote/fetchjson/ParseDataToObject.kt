package com.sun_asterisk.moviedb_50.data.source.remote.fetchjson

import com.sun_asterisk.moviedb_50.data.model.*
import org.json.JSONObject

object ParseDataToObject {
    fun parJsonToMovieResultPage(jsonObject: JSONObject): MovieResultPage {
        return MovieResultPage(jsonObject)
    }

    fun parJsonToMovies(jsonObject: JSONObject): MutableList<Movie> {
        val list = mutableListOf<Movie>()
        val jsonArray = jsonObject.getJSONArray(Movie.MovieEntry.MOVIE)
        for (i in 0 until jsonArray.length()) {
            list.add(Movie(jsonArray.getJSONObject(i)))
        }
        return list
    }

    fun parJsonToGenres(jsonObject: JSONObject): MutableList<Genres> {
        val list = mutableListOf<Genres>()
        val jsonArray = jsonObject.getJSONArray(Genres.GenresEntry.GENRES)
        for (i in 0 until jsonArray.length()) {
            list.add(Genres(jsonArray.getJSONObject(i)))
        }
        return list
    }

    fun parJsonToCasts(jsonObject: JSONObject): MutableList<Cast> {
        val list = mutableListOf<Cast>()
        val jsonObjectCredits = jsonObject.getJSONObject(Cast.CastEntry.CREDITS)
        val jsonArray = jsonObjectCredits.getJSONArray(Cast.CastEntry.CAST)
        for (i in 0 until jsonArray.length()) {
            list.add(Cast(jsonArray.getJSONObject(i)))
        }
        return list
    }

    fun parJsonToProduce(jsonObject: JSONObject): MutableList<Produce> {
        val list = mutableListOf<Produce>()
        val jsonArray = jsonObject.getJSONArray(Produce.ProduceEntry.PRODUCES)
        for (i in 0 until jsonArray.length()) {
            list.add(Produce(jsonArray.getJSONObject(i)))
        }
        return list
    }

    fun parJsonToTrailer(jsonObject: JSONObject): MutableList<MovieTrailer> {
        val list = mutableListOf<MovieTrailer>()
        val jsonObjectVideo =
            jsonObject.getJSONObject(MovieTrailer.MovieTrailerEntry.MOVIE_TRAILER_VIDEO)
        val jsonArray =
            jsonObjectVideo.getJSONArray(MovieTrailer.MovieTrailerEntry.MOVIE_TRAILER_RESULTS)
        for (i in 0 until jsonArray.length()) {
            list.add(MovieTrailer(jsonArray.getJSONObject(i)))
        }
        return list
    }
}