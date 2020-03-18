package com.sun_asterisk.moviedb_50.data.model

import org.json.JSONObject

data class MovieResultPage(
    val moviePage: Int,
    val movieTotalResult: Int,
    val movieTotalPage: Int
) {
    constructor(json: JSONObject) : this(
        moviePage = json.getInt(MovieMovieResultPageEntry.PAGE),
        movieTotalResult = json.getInt(MovieMovieResultPageEntry.TOTAL_RESULT),
        movieTotalPage = json.getInt(MovieMovieResultPageEntry.TOTAL_PAGES)
    )

    object MovieMovieResultPageEntry {
        const val PAGE = "page"
        const val TOTAL_RESULT = "total_results"
        const val TOTAL_PAGES = "total_pages"
    }
}
