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
    private lateinit var tabLayout: TabLayout
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
            Toast.makeText(activity, getString(R.string.check_internet_fail), Toast.LENGTH_LONG)
                .show()
        }
        return view
    }

    override fun onGetGenresSuccess(genres: MutableList<Genres>?) {
        genres?.let { initTabGenres(it) }
    }

    override fun onGetMovieSuccess(movies: MutableList<Movie>?) {
    }

    override fun onError(exception: Exception?) {
    }

    private fun initView(view: View) {
        presenter?.setView(this)
        presenter?.getGenres()
        tabLayout = view.findViewById(R.id.movieByKeyTabLayout)
    }

    private fun initTabGenres(genres: MutableList<Genres>) {
        activity?.let {
            tabLayout.setTabTextColors(
                ContextCompat.getColor(it, R.color.colorTextLightBlue),
                ContextCompat.getColor(it, R.color.colorOrange)
            )
        }
        for (i in 0 until genres.size) {
            tabLayout.addTab(tabLayout.newTab().setText(genres[i].genresName))
        }
    }
}
