package com.sun_asterisk.moviedb_50.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.local.LocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.RemoteDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.Repository
import com.sun_asterisk.moviedb_50.utils.NetworkUtil

class HomeFragment : Fragment(),
    HomeContract.View {
    lateinit var tableLayout: TabLayout
    private var presenter: HomeContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
            val repository: Repository =
                Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource)
            presenter = HomePresenter(repository)
            initView(view)
        } else {
            Toast.makeText(activity, getString(R.string.internet_fail), Toast.LENGTH_LONG).show()
        }
        return view
    }

    override fun onGetGenresSuccess(genres: MutableList<Genres>?) {
        genres?.let { initTabGenres(genres) }
    }

    override fun onGetMovieSuccess(movies: MutableList<Movie>?) {
    }

    override fun onError(exception: Exception?) {
    }

    private fun initView(view: View) {
        presenter?.setView(this)
        presenter?.getGenres()
        tableLayout = view.findViewById(R.id.tabMovieByKey)
    }

    private fun initTabGenres(genres: MutableList<Genres>) {
        tableLayout.setTabTextColors(
            ContextCompat.getColor(activity!!, R.color.colorTextLightBlue),
            ContextCompat.getColor(activity!!, R.color.colorOrange)
        )
        for (i in 0 until genres.size) {
            tableLayout.addTab(tableLayout.newTab().setText(genres[i].genresName))
        }
    }
}
