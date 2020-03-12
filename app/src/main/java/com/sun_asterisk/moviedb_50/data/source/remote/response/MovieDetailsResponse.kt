package com.sun_asterisk.moviedb_50.data.source.remote.response

import org.json.JSONException
import org.json.JSONObject

data class MovieDetailsResponse(
    var movies: Movie,
    var genres: List<Genres>,
    var produce: List<Produces>,
    var casts: List<Casts>
) {
    constructor(jsonObject: JSONObject) : this(
        movies = Movie(jsonObject),
        genres = mutableListOf<Genres>().apply {
            try {
                val jsonArray = jsonObject.getJSONArray(GenresResponse.GenresEntry.GENRES_LIST)
                for (i in 0 until jsonArray.length()) {
                    add(Genres(jsonArray.getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
        produce = mutableListOf<Produces>().apply {
            try {
                val jsonArray = jsonObject.getJSONArray(ProduceEntry.PRODUCES)
                for (i in 0 until jsonArray.length()) {
                    add(Produces(jsonArray.getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        },
        casts = mutableListOf<Casts>().apply {
            try {
                val jsonObjectCredits = jsonObject.getJSONObject(CastEntry.CREDITS)
                val jsonArray = jsonObjectCredits.getJSONArray(CastEntry.CAST)
                for (i in 0 until jsonArray.length()) {
                    add(Casts(jsonArray.getJSONObject(i)))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    )

    data class Movie(
        val movieID: Int,
        val movieTitle: String,
        val movieOverView: String,
        val moviePosterPath: String,
        val movieBackdropPath: String,
        val movieVoteAverage: Double,
        val movieReleaseDate: String

    ) {
        constructor(movieJson: JSONObject) : this(
            movieID = movieJson.optInt(MovieEntry.ID),
            movieTitle = movieJson.optString(MovieEntry.TITLE),
            movieOverView = movieJson.optString(MovieEntry.OVERVIEW),
            moviePosterPath = movieJson.optString(MovieEntry.POSTER_PATH),
            movieBackdropPath = movieJson.optString(MovieEntry.BACKDROP_PATH),
            movieVoteAverage = movieJson.optDouble(MovieEntry.VOTE_AVERAGE),
            movieReleaseDate = movieJson.optString(MovieEntry.RELEASE_DATE)
        )
    }

    data class Genres(
        val genresID: Int,
        val genresName: String
    ) {
        constructor(genresJson: JSONObject) : this(
            genresID = genresJson.optInt(GenresResponse.GenresEntry.GENRES_ID),
            genresName = genresJson.optString(GenresResponse.GenresEntry.GENRES_NAME)
        )
    }


    data class Casts(
        val castId: Int,
        val castName: String,
        val castProfilePath: String
    ) {
        constructor(castJson: JSONObject) : this(
            castId = castJson.optInt(CastEntry.ID),
            castName = castJson.optString(CastEntry.NAME),
            castProfilePath = castJson.optString(CastEntry.PROFILE_PATH)
        )
    }

    data class Produces(
        val produceID: Int,
        val produceLogo: String,
        val produceName: String,
        val produceCountry: String
    ) {
        constructor(produceJson: JSONObject) : this(
            produceID = produceJson.optInt(ProduceEntry.ID),
            produceLogo = produceJson.optString(ProduceEntry.LOGO),
            produceName = produceJson.optString(ProduceEntry.NAME),
            produceCountry = produceJson.optString(ProduceEntry.COUNTRY)
        )
    }

    object MovieEntry {
        const val ID = "id"
        const val TITLE = "title"
        const val OVERVIEW = "overview"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val VOTE_AVERAGE = "vote_average"
        const val RELEASE_DATE = "release_date"
    }

    object ProduceEntry {
        const val PRODUCES = "production_companies"
        const val ID = "id"
        const val LOGO = "logo_path"
        const val NAME = "name"
        const val COUNTRY = "origin_country"
    }

    object CastEntry {
        const val CREDITS = "credits"
        const val CAST = "cast"
        const val ID = "cast_id"
        const val NAME = "name"
        const val PROFILE_PATH = "profile_path"
    }
}
