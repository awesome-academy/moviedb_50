package com.sun_asterisk.moviedb_50.data.model


data class Movie(
    var movieID: Int?,
    var movieTitle: String?,
    var movieOverview: String?,
    var moviePosterPath: String?,
    var movieBackdropPath: String?,
    var movieVoteCount: Int?,
    var movieVoteAverage: Double?,
    var moviePopularity: Double?,
    var movieReleaseDate: String?,
    val movieGenreID: IntArray?
) {
    object MovieEntry {
        const val MOVIE = "results"
        const val ID = "id"
        const val TITLE = "title"
        const val OVERVIEW = "overview"
        const val POSTER_PATH = "poster_path"
        const val BACKDROP_PATH = "backdrop_path"
        const val VOTE_COUNT = "vote_count"
        const val VOTE_AVERAGE = "vote_average"
        const val POPULARITY = "popularity"
        const val RELEASE_DATE = "release_date"
    }

    override fun toString(): String {
        return "Movie(mId=$movieID, mTitle=$movieTitle, mOverview=$movieOverview, mPosterPath=$moviePosterPath, mBackdropPath=$movieBackdropPath, mVoteCount=$movieVoteCount, mVoteAverage=$movieVoteAverage, mPopularity=$moviePopularity, mReleaseDate=$movieReleaseDate, mGenreId=${movieGenreID?.contentToString()})"
    }

}
