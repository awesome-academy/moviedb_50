package com.sun_asterisk.moviedb_50.screen.details

import com.sun_asterisk.moviedb_50.data.model.*
import com.sun_asterisk.moviedb_50.screen.base.BasePresenter

class MovieDetailsContract {
    /**
     * View
     */
    interface View {
        fun onGetMovieSuccess(movie: Movie)
        fun onGetGenresSuccess(genres: List<Genres>)
        fun onGetCastsSuccess(casts: List<Cast>)
        fun onGetProducesSuccess(produces: List<Produce>)
        fun onGetMovieTrailerSuccess(movieTrailers: List<MovieTrailer>)
        fun showFavoriteImage(type: String)
        fun notifyFavorite(type: String)
        fun onLoading(isLoad: Boolean)
        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getMovieDetails(movieID: Int)
        fun handleFavorites(favorite: Favorite)
    }
}
