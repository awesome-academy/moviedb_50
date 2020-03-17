package com.sun_asterisk.moviedb_50.data.repository

import com.sun_asterisk.moviedb_50.data.source.MovieDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
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
        query: String,
        page: Int,
        listener: OnFetchDataJsonListener<MoviesResponse>
    ) {
        remoteMovieDataSource.getMovies(type, query, page, listener)
    }

    fun getMovieDetails(
        movieID: Int,
        listener: OnFetchDataJsonListener<MovieDetailsResponse>?
    ) {
        listener?.let { remoteMovieDataSource.getMovieDetails(movieID, it) }
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
