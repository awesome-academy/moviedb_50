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
import com.sun_asterisk.moviedb_50.utils.OnClickListener
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment(),
    HomeContract.View, OnClickListener<Movie> {
    private var presenter: HomeContract.Presenter? = null
    private var upcomingAdapter = MovieAdapter()
    private var popularAdapter = MovieAdapter()

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
        initView()
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
        initTabGenres(genres)
    }

    override fun onGetMoviesNowPlayingSuccess(movies: List<Movie>) {
        initSlideViewPaper(movies)
    }

    override fun onGetMoviesUpcomingSuccess(movies: List<Movie>) {
        upcomingAdapter.updateData(movies)
    }

    override fun onGetMoviesPopularSuccess(movies: List<Movie>) {
        popularAdapter.updateData(movies)
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(activity, exception?.message.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun click(item: Movie?) {}

    private fun initTabGenres(genres: List<Genres>) {
        activity?.let {
            view?.movieByKeyTabLayout?.setTabTextColors(
                ContextCompat.getColor(it, R.color.colorTextLightBlue),
                ContextCompat.getColor(it, R.color.colorOrange)
            )
        }
        for (element in genres) {
            view?.let {
                it.movieByKeyTabLayout.addTab(
                    it.movieByKeyTabLayout.newTab().setText(element.genresName)
                )
            }
        }
    }

    private fun initSlideViewPaper(movies: List<Movie>) {
        val adapter = SliderViewPagerAdapter(movies)
        view?.sliderViewPager?.adapter = adapter
        adapter.setSlideItemClickListener(this)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    view?.run {
                        if (sliderViewPager.currentItem < movies.size - 1) {
                            sliderViewPager.currentItem =
                                sliderViewPager.currentItem + 1
                        } else {
                            sliderViewPager.currentItem = 0
                        }
                    }
                }
            }
        }, Constant.DELAY_SLIDE, Constant.DELAY_SLIDE)
        view?.run {
            indicatorTabLayout.setupWithViewPager(
                sliderViewPager,
                true
            )
        }
    }

    private fun initView() {
        view?.run {
            popularRecyclerView.setHasFixedSize(true)
            popularRecyclerView.adapter = popularAdapter.apply {
                onItemClick = { item, position ->
                    toast("Popular($position) : ${item.movieTitle}")
                }
            }
            upcomingRecyclerView.setHasFixedSize(true)
            upcomingRecyclerView.adapter = upcomingAdapter.apply {
                onItemClick = { item, position ->
                    toast("Upcoming($position) : ${item.movieTitle}")
                }
            }
        }
        presenter?.setView(this)
        presenter?.onStart()
    }

    private fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
