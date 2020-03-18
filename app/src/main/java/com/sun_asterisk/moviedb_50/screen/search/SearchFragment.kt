package com.sun_asterisk.moviedb_50.screen.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource

class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var presenter: SearchContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance()
            )
        presenter = SearchPresenter(movieRepository)
        presenter.setView(this)
        presenter.onStart()
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
        TODO("Not yet implemented")
    }

    override fun getCategoriesSuccess(categories: List<Category>) {
        TODO("Not yet implemented")
    }

    override fun onGetMoviesTopRatedSuccess(movies: List<Movie>) {
        TODO("Not yet implemented")
    }

    override fun onError(exception: Exception?) {
        TODO("Not yet implemented")
    }

    override fun onLoading(isLoad: Boolean) {
        TODO("Not yet implemented")
    }
}
