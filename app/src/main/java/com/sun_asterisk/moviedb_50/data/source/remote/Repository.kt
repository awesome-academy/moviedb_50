package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.GenresResponse
import com.sun_asterisk.moviedb_50.data.model.MoviesResponse
import com.sun_asterisk.moviedb_50.data.source.DataSource

class Repository private constructor(
    private val remoteDataSource: DataSource.RemoteDataSource,
    private val localDataSource: DataSource.LocalDataSource
) {
    fun getGenres(listener: OnFetchDataJsonListener<GenresResponse>) {
        remoteDataSource.getGenres(listener)
    }

    fun getMovies(
        type: String,
        page: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {
        remoteDataSource.getMovie(type, page, listener)
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: DataSource.LocalDataSource
        ) = Repository(remoteDataSource, localDataSource).also { instance = it }
    }
}
