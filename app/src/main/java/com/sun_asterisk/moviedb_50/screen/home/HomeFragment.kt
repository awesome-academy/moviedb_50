package com.sun_asterisk.moviedb_50.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.local.LocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.RemoteDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.Repository
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment(),
    HomeContract.View {
    private var presenter: HomeContract.Presenter? = null
    private lateinit var viewOfLayout: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
            val repository: Repository =
                Repository.getInstance(RemoteDataSource.getInstance(), LocalDataSource)
            presenter = HomePresenter(repository)
        } else {
            Toast.makeText(activity, getString(R.string.check_internet_fail), Toast.LENGTH_LONG)
                .show()
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewOfLayout = view
        initView()
    }

    override fun onGetGenresSuccess(genres: List<Genres>?) {
        genres?.let { initTabGenres(it) }
    }

    override fun onGetMoviesNowPlayingSuccess(movies: List<Movie>?) {
        movies?.let { initSlideViewPaper(it) }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(activity,exception.toString(),Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        presenter?.setView(this)
        presenter?.onStart()
    }

    private fun initTabGenres(genres: List<Genres>) {
        activity?.let {
            viewOfLayout.movieByKeyTabLayout.setTabTextColors(
                ContextCompat.getColor(it, R.color.colorTextLightBlue),
                ContextCompat.getColor(it, R.color.colorOrange)
            )
        }
        for (element in genres) {
            viewOfLayout.movieByKeyTabLayout.addTab(
                viewOfLayout.movieByKeyTabLayout.newTab().setText(element.genresName)
            )
        }
    }

    private fun initSlideViewPaper(movies: List<Movie>) {
        activity?.let {
            val adapter = SliderViewPagerAdapter(it, movies)
            viewOfLayout.sliderViewPager.adapter = adapter
        }
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    if (viewOfLayout.sliderViewPager.currentItem < movies.size - 1) {
                        viewOfLayout.sliderViewPager.currentItem =
                            viewOfLayout.sliderViewPager.currentItem + 1
                    } else {
                        viewOfLayout.sliderViewPager.currentItem = 0
                    }
                }
            }
        }, Constant.DELAY_SLIDE, Constant.DELAY_SLIDE)
        viewOfLayout.indicatorTabLayout.setupWithViewPager(
            viewOfLayout.sliderViewPager,
            true
        )
    }
}
