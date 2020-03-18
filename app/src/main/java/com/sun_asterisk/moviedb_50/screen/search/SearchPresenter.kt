package com.sun_asterisk.moviedb_50.screen.search

import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse
import com.sun_asterisk.moviedb_50.utils.Constant

class SearchPresenter(private val movieRepository: MovieRepository) : SearchContract.Presenter {
    private var view: SearchContract.View? = null

    override fun getGenres() {
        movieRepository.getGenres(object : OnDataLoadedCallback<GenresResponse> {

            override fun onError(e: Exception) {
                view?.onError(e)
            }

            override fun onSuccess(data: GenresResponse?) {
                data ?: return
                view?.onGetGenresSuccess(data.list)
            }
        })
    }

    override fun getCategories() {
        movieRepository.getCategories(object : OnDataLoadedCallback<List<Category>> {
            override fun onSuccess(data: List<Category>?) {
                data ?: return
                view?.getCategoriesSuccess(data)
            }

            override fun onError(e: Exception) {
                view?.onError(e)
            }
        })
    }

    override fun getMovies(type: String, query: String, page: Int) {
        movieRepository.getMovies(
            type,
            query,
            page,
            object : OnDataLoadedCallback<MoviesResponse> {

                override fun onError(e: Exception) {
                    view?.onError(e)
                }

                override fun onSuccess(data: MoviesResponse?) {
                    data ?: return
                    view?.onGetMoviesTopRatedSuccess(data.list)
                    view?.onLoading(true)
                }
            })
    }

    override fun onStart() {
        getGenres()
        getCategories()
        getMovies(Constant.BASE_TOP_RATE)
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun setView(view: SearchContract.View?) {
        this.view = view
    }
}
