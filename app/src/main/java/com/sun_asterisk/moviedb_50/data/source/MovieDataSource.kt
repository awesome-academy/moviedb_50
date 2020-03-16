package com.sun_asterisk.moviedb_50.data.source

import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse

interface MovieDataSource {
    /**
     * Local
     */
    interface LocalDataSource

    /**
     * Remote
     */
    interface RemoteDataSource {

        fun getGenres(listener: OnFetchDataJsonListener<GenresResponse>)

        fun getMovie(
            type: String,
            page: Int,
            genresID: Int,
            listener: OnFetchDataJsonListener<MoviesResponse>
        )

        fun getMovieDetails(
            movieID: Int,
            listener: OnFetchDataJsonListener<MovieDetailsResponse>
        )
    }
}
