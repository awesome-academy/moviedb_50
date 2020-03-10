package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.GenresResponse
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.model.MoviesResponse
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.Repository
import com.sun_asterisk.moviedb_50.utils.Constant

class HomePresenter(private val repository: Repository) : HomeContract.Presenter {
    private var view: HomeContract.View? = null

    override fun onStart() {
        getGenres()
        getMovie(Constant.BASE_NOW_PLAYING, Constant.BASE_DEFAULT)
    }

    override fun onStop() {}

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override
    fun getGenres() {
        repository.getGenres(object : OnFetchDataJsonListener<GenresResponse> {

            override fun onError(e: Exception) {
                view?.onError(e)
            }

            override fun onSuccess(data: GenresResponse?) {
                data ?: return
                view?.onGetGenresSuccess(data.list.map { Genres(it) })
            }
        })
    }

    override fun getMovie(type: String, page: Int) {
        repository.getMovies(type, page, object : OnFetchDataJsonListener<MoviesResponse> {

            override fun onError(e: Exception) {
                view?.onError(e)
            }

            override fun onSuccess(data: MoviesResponse?) {
                data ?: return
                view?.onGetMoviesNowPlayingSuccess(data.list.map { Movie(it) })
            }
        })
    }
}
