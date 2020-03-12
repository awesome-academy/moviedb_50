package com.sun_asterisk.moviedb_50.screen.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Casts
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.MovieDetails
import com.sun_asterisk.moviedb_50.data.model.Produces
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.utils.NetworkUtil

class MovieDetailsFragment : Fragment(), MovieDetailsContract.View {
    private var presenter: MovieDetailsContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
            val movieRepository: MovieRepository =
                MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(),
                    MovieLocalDataSource
                )
            presenter = MovieDetailsPresenter(movieRepository)
        } else {
            Toast.makeText(activity, getString(R.string.check_internet_fail), Toast.LENGTH_LONG)
                .show()
        }
        initView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onGetMovieSuccess(movieDetails: MovieDetails) {
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
    }

    override fun onGetCastsSuccess(casts: List<Casts>) {
    }

    override fun onGetProducesSuccess(produces: List<Produces>) {
    }

    override fun onError(exception: Exception?) {
    }

    companion object {
        fun getInstance(id: Int) = MovieDetailsFragment().apply {
            arguments = bundleOf("ID" to id)
        }
    }

    private fun initView() {
        presenter?.setView(this)
        arguments?.let { presenter?.getMovieDetails(it.getInt("ID")) }
    }
}
