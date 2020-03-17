package com.sun_asterisk.moviedb_50.screen.listmovie

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.utils.Constant

class ListMovieFragment : Fragment(), ListMovieContract.View {
    private var presenter: ListMovieContract.Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource
            )
        presenter = ListMoviePresenter(movieRepository)
        initPresenter()
    }

    override fun onGetMoviesSuccess(movies: List<Movie>) {
        TODO("Not yet implemented")
    }

    override fun onError(exception: Exception?) {
        TODO("Not yet implemented")
    }

    override fun onLoading(isLoad: Boolean) {
        TODO("Not yet implemented")
    }

    private fun initPresenter() {
        presenter?.setView(this)
        arguments?.run {
            val type = getString(Constant.BASE_TYPE)
            val value = getString(Constant.BASE_VALUE)
            if (type != null && value != null) presenter?.getMovies(type, value)
        }
    }

    companion object {
        fun getInstance(type: String, query: String, title: String) =
            ListMovieFragment().apply {
                arguments = bundleOf(
                    Constant.BASE_TYPE to type,
                    Constant.BASE_VALUE to query,
                    Constant.BASE_TITLE to title
                )
            }
    }
}
