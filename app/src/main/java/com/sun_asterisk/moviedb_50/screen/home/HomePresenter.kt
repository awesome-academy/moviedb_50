package com.sun_asterisk.moviedb_50.screen.home

import android.util.Log
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.remote.OnFetchDataJsonListener
import com.sun_asterisk.moviedb_50.data.source.remote.Repository


class HomePresenter(private val mRepository: Repository?) :
    HomeContract.Presenter {
    private var view: HomeContract.View? = null
    override fun onStart() {

    }

    override fun onStop() {}
    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override
    fun getGenres() {
        mRepository?.getGenres(object :
            OnFetchDataJsonListener<Genres> {

            override fun onError(e: Exception) {
                Log.i("onError", e.toString())
                view!!.onError(e)
            }

            override fun onSuccess(data: MutableList<Genres>?) {
                view!!.onGetGenresSuccess(data)
            }
        })
    }

    override fun getMovie(type: String) {
        mRepository?.getMovies(object :
            OnFetchDataJsonListener<Movie> {

            override fun onError(e: Exception) {
                Log.i("onError", e.toString())
                view!!.onError(e)
            }

            override fun onSuccess(data: MutableList<Movie>?) {
                view!!.onGetMovieSuccess(data)
            }
        }, type)
    }
}
