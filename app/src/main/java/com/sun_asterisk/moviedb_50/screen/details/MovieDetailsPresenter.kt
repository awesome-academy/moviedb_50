package com.sun_asterisk.moviedb_50.screen.details

import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse

class MovieDetailsPresenter(private val movieRepository: MovieRepository) :
    MovieDetailsContract.Presenter {
    private var view: MovieDetailsContract.View? = null

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun setView(view: MovieDetailsContract.View?) {
        this.view = view
    }

    override fun getMovieDetails(movieID: Int) {
        view?.onLoading(false)
        movieRepository.getMovieDetails(
            movieID,
            object : OnDataLoadedCallback<MovieDetailsResponse> {
                override fun onError(e: Exception) {
                    view?.onError(e)
                }

                override fun onSuccess(data: MovieDetailsResponse?) {
                    data ?: return
                    view?.run {
                        onGetCastsSuccess(data.casts)
                        onGetMovieSuccess(data.movies)
                        onGetProducesSuccess(data.produce)
                        onGetGenresSuccess(data.genres)
                        onGetMovieTrailerSuccess(data.trailers)
                    }
                    view?.onLoading(true)
                }
            })
    }
}
