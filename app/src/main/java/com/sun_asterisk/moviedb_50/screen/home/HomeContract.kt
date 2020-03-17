package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.screen.base.BasePresenter
import com.sun_asterisk.moviedb_50.utils.Constant

interface HomeContract {
    /**
     * View
     */
    interface View {
        fun onGetGenresSuccess(genres: List<Genres>)
        fun onGetMoviesNowPlayingSuccess(movies: List<Movie>)
        fun onGetMoviesUpcomingSuccess(movies: List<Movie>)
        fun onGetMoviesPopularSuccess(movies: List<Movie>)
        fun onGetMoviesByGenresIDSuccess(movies: List<Movie>)
        fun onError(exception: Exception?)
        fun onLoading(isLoad: Boolean)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getGenres()
        fun getMovie(
            type: String,
            query: String = Constant.BASE_QUERY_DEFAULT,
            page: Int = Constant.BASE_PAGE_DEFAULT

        )
    }
}
