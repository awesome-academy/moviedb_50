package com.sun_asterisk.moviedb_50.screen.details

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.*
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil

class MovieDetailsFragment : Fragment(), MovieDetailsContract.View {
    private lateinit var presenter: MovieDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource
            )
        presenter = MovieDetailsPresenter(movieRepository)
        initPresenter()
    }

    override fun onGetMovieSuccess(movie: Movie) {
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
    }

    override fun onGetCastsSuccess(casts: List<Cast>) {
    }

    override fun onGetProducesSuccess(produces: List<Produce>) {
    }

    override fun onGetMovieTrailerSuccess(movieTrailers: List<MovieTrailer>) {
    }

    override fun onLoading(isLoad: Boolean) {
    }

    override fun onError(exception: Exception?) {
        toast(exception?.message.toString())
    }

    private fun initPresenter() {
        presenter.setView(this)
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
            arguments?.let { presenter.getMovieDetails(it.getInt(Constant.BASE_MOVIE_ID)) }
        } else {
            onLoading(true)
            toast(getString(R.string.check_internet_fail))
        }
    }

    companion object {
        fun getInstance(id: Int) = MovieDetailsFragment().apply {
            arguments = bundleOf(Constant.BASE_MOVIE_ID to id)
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG)
            .show()
    }
}
