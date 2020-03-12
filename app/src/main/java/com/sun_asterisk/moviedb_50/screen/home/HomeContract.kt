package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.base.BasePresenter
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie

interface HomeContract {
    /**
     * View
     */
    interface View {
        fun onGetGenresSuccess(genres: List<Genres>)
        fun onGetMoviesNowPlayingSuccess(movies: List<Movie>)
        fun onGetMoviesUpcomingSuccess(movies: List<Movie>)
        fun onGetMoviesPopularSuccess(movies: List<Movie>)
        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getGenres()
        fun getMovie(type: String, page: Int)
    }
}
