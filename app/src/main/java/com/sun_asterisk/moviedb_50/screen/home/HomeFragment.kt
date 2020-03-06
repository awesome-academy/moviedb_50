package com.sun_asterisk.moviedb_50.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
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
    var presenter: HomeContract.Presenter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) }!!) {
            val repository: Repository? =
                Repository.getsInstance(
                    RemoteDataSource.getsInstance(),
                    LocalDataSource.getsInstance()
                )
            presenter =
                HomePresenter(
                    repository
                )
            initView(view)
        } else {
            Toast.makeText(activity, "Check internet connection!", Toast.LENGTH_LONG)
        }

        return view
    }


    override fun onGetGenresSuccess(List: MutableList<Genres>?) {
    }

    override fun onGetMovieSuccess(List: MutableList<Movie>?) {
        TODO("Not yet implemented")
    }


    override fun onError(exception: Exception?) {
    }


    private fun initView(view: View) {
        //getGenres
        presenter?.setView(this)
        presenter?.getGenres()
    }




}
