package com.sun_asterisk.moviedb_50.screen.details

import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.MovieDetailsResponse
import com.sun_asterisk.moviedb_50.utils.Constant

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
                    isFavoriteMovie(movieID.toString())
                }
            })
    }

    private fun isFavoriteMovie(movieID: String) {
        movieRepository.findFavoriteId(movieID, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean?) {
                data ?: return
                view?.showFavoriteImage(
                    if (data) Constant.BASE_NOTIFY_ADD_FAVORITE_SUCCESS
                    else Constant.BASE_NOTIFY_DELETE_FAVORITE_SUCCESS
                )
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }

        })
    }

    override fun handleFavorites(favorite: Favorite) {
        movieRepository.findFavoriteId(favorite.movieID, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean?) {
                data ?: return
                if (data) deleteFavorite(favorite.movieID) else addFavorite(favorite)
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }

    private fun addFavorite(favorite: Favorite) {
        movieRepository.addFavorite(favorite, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean?) {
                data ?: return
                view?.run {
                    if (data) {
                        showFavoriteImage(Constant.BASE_NOTIFY_ADD_FAVORITE_SUCCESS)
                        notifyFavorite(Constant.BASE_NOTIFY_ADD_FAVORITE_SUCCESS)
                    } else {
                        notifyFavorite(Constant.BASE_NOTIFY_ADD_FAVORITE_ERROR)
                    }
                }
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }

    private fun deleteFavorite(movieID: String) {
        movieRepository.deleteFavorite(movieID, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean?) {
                data ?: return
                view?.run {
                    if (data) {
                        showFavoriteImage(Constant.BASE_NOTIFY_DELETE_FAVORITE_SUCCESS)
                        notifyFavorite(Constant.BASE_NOTIFY_DELETE_FAVORITE_SUCCESS)
                    } else {
                        notifyFavorite(Constant.BASE_NOTIFY_DELETE_FAVORITE_ERROR)
                    }
                }
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }
}
