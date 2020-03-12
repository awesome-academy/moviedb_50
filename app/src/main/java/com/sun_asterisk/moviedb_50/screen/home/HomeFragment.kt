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
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.home.adapter.MovieAdapter
import com.sun_asterisk.moviedb_50.screen.home.adapter.SliderViewPagerAdapter
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import com.sun_asterisk.moviedb_50.utils.OnClickListener
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment(),
    HomeContract.View, OnClickListener<Movie> {
    private var presenter: HomeContract.Presenter? = null
    private val upcomingAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val popularAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val movieByIDAdapter: MovieAdapter by lazy { MovieAdapter() }

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
            presenter = HomePresenter(movieRepository)
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

    override fun onGetMoviesByGenresIDSuccess(movies: List<Movie>) {
        movieByIDAdapter.updateData(movies)
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(activity, exception?.message.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun click(item: Movie?) {}

    private fun initTabGenres(genres: List<Genres>) {
        presenter?.getMovie(
            Constant.BASE_MOVIE_BY_ID,
            Constant.BASE_PAGE_DEFAULT,
            genres[0].genresID
        )
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
        view?.movieByKeyTabLayout?.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // TODO: Handle <some thing>
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        presenter?.getMovie(
                            Constant.BASE_MOVIE_BY_ID,
                            Constant.BASE_PAGE_DEFAULT,
                            genres[it.position].genresID
                        )
                    }
                }
            })
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
                }
            }
            upcomingRecyclerView.setHasFixedSize(true)
            upcomingRecyclerView.adapter = upcomingAdapter.apply {
                onItemClick = { item, position ->
                }
            }
            movieByGenresRecyclerView.setHasFixedSize(true)
            movieByGenresRecyclerView.adapter = movieByIDAdapter.apply {
                onItemClick = { item, position ->
                }
            }
        }
        presenter?.setView(this)
        presenter?.onStart()
    }
}
