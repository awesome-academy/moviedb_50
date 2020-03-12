package com.sun_asterisk.moviedb_50.screen.details

import com.sun_asterisk.moviedb_50.data.model.Casts
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.MovieDetails
import com.sun_asterisk.moviedb_50.data.model.Produces
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

class MovieDetailsPresenter(private val movieRepository: MovieRepository) :
    MovieDetailsContract.Presenter {
    private var view: MovieDetailsContract.View? = null

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: MovieDetailsContract.View?) {
        this.view = view
    }

    override fun getMovieDetails(movieID: Int) {
        movieRepository.getMovieDetails(
            movieID,
            object : OnFetchDataJsonListener<MovieDetailsResponse> {
                override fun onError(e: Exception) {
                    view?.onError(e)
                }

                override fun onSuccess(data: MovieDetailsResponse?) {
                    data ?: return
                    val movie = MovieDetails(data.movies)
                    val casts = data.casts.map { Casts(it) }
                    val produces = data.produce.map { Produces(it) }
                    val genres = data.genres.map { Genres(it) }
                    view?.run {
                        onGetCastsSuccess(casts)
                        onGetMovieSuccess(movie)
                        onGetProducesSuccess(produces)
                        onGetGenresSuccess(genres)
                    }
                }
            })
    }
}
