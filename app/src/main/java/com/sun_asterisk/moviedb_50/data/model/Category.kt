package com.sun_asterisk.moviedb_50.data.model

data class Category(val categoryName: String, val categoryImage: Int) {
    object CategoryEntry {
        const val POPULAR = "Popular"
        const val TOP_RATE = "Top rated"
        const val NOW_PLAYING = "Now playing"
        const val UPCOMING = "Upcoming"
    }
}
