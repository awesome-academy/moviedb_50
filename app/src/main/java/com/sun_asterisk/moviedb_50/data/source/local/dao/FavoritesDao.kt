package com.sun_asterisk.moviedb_50.data.source.local.dao

import com.sun_asterisk.moviedb_50.data.model.Favorite

interface FavoritesDao {
    fun getAllFavorites(): MutableList<Favorite>
    fun addFavorite(favorite: Favorite): Boolean
    fun deleteFavorite(movieId: String): Boolean
    fun findFavorite(movieId: String): Boolean
}
