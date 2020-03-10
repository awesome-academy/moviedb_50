package com.sun_asterisk.moviedb_50.data.source

import com.sun_asterisk.moviedb_50.data.model.GenresResponse
import com.sun_asterisk.moviedb_50.data.model.MoviesResponse
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener

interface DataSource {
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
            listener: OnFetchDataJsonListener<MoviesResponse>
        )
    }
}
