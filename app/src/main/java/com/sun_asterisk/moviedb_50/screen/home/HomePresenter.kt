package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.Repository

class HomePresenter(private val repository: Repository) : HomeContract.Presenter {
    private var view: HomeContract.View? = null

    override fun onStart() {}
    override fun onStop() {}
    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override
    fun getGenres() {
        repository.getGenres(object : OnFetchDataJsonListener<MutableList<Genres>> {

            override fun onError(e: Exception) {
                view?.onError(e)
            }

            override fun onSuccess(data: MutableList<Genres>?) {
                view?.onGetGenresSuccess(data)
            }
        })
    }

    override fun getMovies(type: String, genresID: Int) {
        repository.getMovies(type, object : OnFetchDataJsonListener<MutableList<Movie>> {

            override fun onError(e: Exception) {
                view?.onError(e)
            }

            override fun onSuccess(data: MutableList<Movie>?) {
                view?.onGetMovieSuccess(data)
            }
        })
    }
}
