package com.sun_asterisk.moviedb_50.screen.favorite

import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.utils.Constant

class FavoritePresenter(private val movieRepository: MovieRepository) : FavoriteContract.Presenter {
    private var view: FavoriteContract.View? = null

    override fun getFavorite() {
        view?.onLoading(false)
        movieRepository.getFavorites(object : OnDataLoadedCallback<MutableList<Favorite>> {
            override fun onSuccess(data: MutableList<Favorite>?) {
                data ?: return
                view?.onGetFavoritesSuccess(data)
                view?.onLoading(true)
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }

    override fun deleteFavorite(position: Int, favoriteId: String) {
        movieRepository.deleteFavorite(favoriteId, object : OnDataLoadedCallback<Boolean> {
            override fun onSuccess(data: Boolean?) {
                data ?: return
                if (data) {
                    view?.notifyDeleteFavorite(Constant.BASE_NOTIFY_DELETE_FAVORITE_SUCCESS)
                    view?.updateFavoritesAfterRemovingItem(position)
                } else {
                    view?.notifyDeleteFavorite(Constant.BASE_NOTIFY_DELETE_FAVORITE_ERROR)
                }
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: FavoriteContract.View?) {
        this.view = view
    }
}
