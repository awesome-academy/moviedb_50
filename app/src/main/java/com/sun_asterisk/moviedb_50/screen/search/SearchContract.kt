package com.sun_asterisk.moviedb_50.screen.search

import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.screen.base.BasePresenter
import com.sun_asterisk.moviedb_50.utils.Constant

interface SearchContract {
    /**
     * View
     */
    interface View {
        fun onGetGenresSuccess(genres: List<Genres>)
        fun getCategoriesSuccess(categories: List<Category>)
        fun onGetMoviesTopRatedSuccess(movies: List<Movie>)
        fun onError(exception: Exception?)
        fun onLoading(isLoad: Boolean)
    }

    /**
     * Presenter
     */
    interface Presenter : BasePresenter<View?> {
        fun getGenres()
        fun getCategories()
        fun getMovies(
            type: String,
            query: String = Constant.BASE_QUERY_DEFAULT,
            page: Int = Constant.BASE_PAGE_DEFAULT
        )
    }
}
