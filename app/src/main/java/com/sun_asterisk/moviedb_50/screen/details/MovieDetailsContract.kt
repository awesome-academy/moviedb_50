package com.sun_asterisk.moviedb_50.screen.details

import com.sun_asterisk.moviedb_50.data.model.Casts
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.MovieDetails
import com.sun_asterisk.moviedb_50.data.model.Produces
import com.sun_asterisk.moviedb_50.screen.base.BasePresenter
import com.sun_asterisk.moviedb_50.utils.Constant

class MovieDetailsContract {
    /**
     * View
     */
    interface View {
        fun onGetMovieSuccess(movieDetails: MovieDetails)
        fun onGetGenresSuccess(genres: List<Genres>)
        fun onGetCastsSuccess(casts: List<Casts>)
        fun onGetProducesSuccess(produces: List<Produces>)
        fun onError(exception: Exception?)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getMovieDetails(
            movieID: Int
        )
    }
}
