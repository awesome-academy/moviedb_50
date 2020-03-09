package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.utils.BasePresenter

interface HomeContract {
    /**
     * View
     */
    interface View {
        fun onGetGenresSuccess(genres: MutableList<Genres>?)
        fun onGetMovieSuccess(movies: MutableList<Movie>?)
        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getGenres()
        fun getMovies(type: String, genresID:Int)
    }
}
