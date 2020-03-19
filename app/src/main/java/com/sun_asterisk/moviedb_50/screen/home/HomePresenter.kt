package com.sun_asterisk.moviedb_50.screen.home

import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.remote.OnDataLoadedCallback
import com.sun_asterisk.moviedb_50.data.source.remote.response.GenresResponse
import com.sun_asterisk.moviedb_50.data.source.remote.response.MoviesResponse
import com.sun_asterisk.moviedb_50.utils.Constant

class HomePresenter(private val movieRepository: MovieRepository) : HomeContract.Presenter {
    private var view: HomeContract.View? = null

    override fun onStart() {
        view?.onLoading(false)
        getGenres()
        getMovie(Constant.BASE_NOW_PLAYING)
        getMovie(Constant.BASE_UPCOMING)
        getMovie(Constant.BASE_POPULAR)
    }

    override fun onStop() {}

    override fun setView(view: HomeContract.View?) {
        this.view = view
    }

    override
    fun getGenres() {
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

    override fun getMovie(type: String, query: String, page: Int) {
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
                    when (type) {
                        Constant.BASE_NOW_PLAYING ->
                            view?.onGetMoviesNowPlayingSuccess(data.list)
                        Constant.BASE_UPCOMING ->
                            view?.onGetMoviesUpcomingSuccess(data.list)
                        Constant.BASE_POPULAR ->
                            view?.onGetMoviesPopularSuccess(data.list)
                        Constant.BASE_GENRES_ID ->
                            view?.onGetMoviesByGenresIDSuccess(data.list)
                    }
                    view?.onLoading(true)
                }
            })
    }
}
