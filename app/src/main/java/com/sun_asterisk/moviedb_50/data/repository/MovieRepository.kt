package com.sun_asterisk.moviedb_50.data.repository

import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.data.source.MovieDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse

class MovieRepository private constructor(
    private val remoteMovie: MovieDataSource.Remote,
    private val localMovie: MovieDataSource.Local
) {
    fun getCategories(listener: OnDataLoadedCallback<List<Category>>) {
        localMovie.getCategories(listener)
    }

    fun getGenres(listener: OnDataLoadedCallback<GenresResponse>) {
        remoteMovie.getGenres(listener)
    }

    fun getMovies(
        type: String,
        query: String,
        page: Int,
        listener: OnDataLoadedCallback<MoviesResponse>
    ) {
        remoteMovie.getMovies(type, query, page, listener)
    }

    fun getMovieDetails(
        movieID: Int,
        listener: OnDataLoadedCallback<MovieDetailsResponse>?
    ) {
        listener?.let { remoteMovie.getMovieDetails(movieID, it) }
    }

    fun getFavorites(listener: OnDataLoadedCallback<MutableList<Favorite>>) {
        localMovie.getFavorites(listener)
    }

    fun addFavorite(favorite: Favorite, listener: OnDataLoadedCallback<Boolean>) {
        localMovie.addFavorite(favorite, listener)
    }

    fun deleteFavorite(movieID: String, listener: OnDataLoadedCallback<Boolean>) {
        localMovie.deleteFavorite(movieID, listener)
    }

    fun findFavoriteId(movieID: String, listener: OnDataLoadedCallback<Boolean>) {
        localMovie.findFavoriteId(movieID, listener)
    }

    companion object {
        private var instance: MovieRepository? = null
        fun getInstance(
            movieRemoteDataSource: MovieRemoteDataSource,
            localMovieDataSource: MovieDataSource.Local
        ) = instance ?: MovieRepository(
            movieRemoteDataSource,
            localMovieDataSource
        ).also { instance = it }
    }
}
