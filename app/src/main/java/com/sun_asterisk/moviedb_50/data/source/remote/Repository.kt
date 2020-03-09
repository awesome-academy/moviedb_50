package com.sun_asterisk.moviedb_50.data.source.remote

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.DataSource

class Repository private constructor(
    private val remoteDataSource: DataSource.RemoteDataSource,
    private val localDataSource: DataSource.LocalDataSource
) {
    fun getGenres(onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Genres>>?) {
        onFetchDataJsonListener?.let{remoteDataSource.getGenres(it)}
    }

    fun getMovies(
        type: String,
        onFetchDataJsonListener: OnFetchDataJsonListener<MutableList<Movie>>?
    ) {
        onFetchDataJsonListener?.let {remoteDataSource.getMovie(type, it)  }
    }

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: DataSource.LocalDataSource
        ) = Repository(remoteDataSource, localDataSource).also { instance = it }
    }
}
