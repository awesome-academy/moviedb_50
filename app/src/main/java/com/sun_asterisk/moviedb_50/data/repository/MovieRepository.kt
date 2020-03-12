package com.sun_asterisk.moviedb_50.data.repository

import com.sun_asterisk.moviedb_50.data.source.MovieDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse

class MovieRepository private constructor(
    private val remoteMovieDataSource: MovieDataSource.RemoteDataSource,
    private val localMovieDataSource: MovieDataSource.LocalDataSource
) {
    fun getGenres(listener: OnFetchDataJsonListener<GenresResponse>) {
        remoteMovieDataSource.getGenres(listener)
    }

    fun getMovies(
        type: String,
        page: Int,
        genresID: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {
        remoteMovieDataSource.getMovie(type, page, genresID, listener)
    }

    companion object {
        private var instance: MovieRepository? = null
        fun getInstance(
            movieRemoteDataSource: MovieRemoteDataSource,
            localMovieDataSource: MovieDataSource.LocalDataSource
        ) = instance ?: MovieRepository(
            movieRemoteDataSource,
            localMovieDataSource
        ).also { instance = it }
    }
}
