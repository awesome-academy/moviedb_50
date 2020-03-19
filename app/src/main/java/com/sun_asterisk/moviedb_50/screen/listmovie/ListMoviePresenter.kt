package com.sun_asterisk.moviedb_50.screen.listmovie

import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse
import com.sun_asterisk.moviedb_50.utils.Constant

class ListMoviePresenter(private val movieRepository: MovieRepository) :
    ListMovieContract.Presenter {
    private var view: ListMovieContract.View? = null

    override fun setView(view: ListMovieContract.View?) {
        this.view = view
    }

    override fun getMovies(type: String, query: String, page: Int) {
        if (page == Constant.BASE_PAGE_DEFAULT) {
            view?.onLoading(false)
        }
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
                    view?.onGetMovieResultPage(data.movieResultPage)
                    view?.onGetMoviesSuccess(data.list)
                    view?.onLoading(true)
                }
            })
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }
}
