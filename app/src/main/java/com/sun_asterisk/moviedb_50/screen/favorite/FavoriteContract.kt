package com.sun_asterisk.moviedb_50.screen.favorite

import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.screen.base.BasePresenter

interface FavoriteContract {
    /**
     * View
     */
    interface View {
        fun onGetFavoritesSuccess(favorites: MutableList<Favorite>)
        fun updateFavoritesAfterRemovingItem(position: Int)
        fun notifyDeleteFavorite(type: String)
        fun onError(exception: Exception?)
        fun onLoading(isLoad: Boolean)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getFavorite()
        fun deleteFavorite(position: Int, favoriteId: String)
    }
}
