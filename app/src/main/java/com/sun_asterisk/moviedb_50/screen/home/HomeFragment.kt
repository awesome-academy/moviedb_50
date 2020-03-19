package com.sun_asterisk.moviedb_50.screen.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.details.MovieDetailsFragment
import com.sun_asterisk.moviedb_50.screen.home.adapter.MovieAdapter
import com.sun_asterisk.moviedb_50.screen.home.adapter.SliderViewPagerAdapter
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import com.sun_asterisk.moviedb_50.utils.OnClickListener
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*

class HomeFragment : Fragment(),
    HomeContract.View, OnClickListener<Movie> {
    private lateinit var presenter: HomeContract.Presenter
    private val upcomingAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val popularAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val movieByIDAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val movieSlideAdapter: SliderViewPagerAdapter by lazy { SliderViewPagerAdapter() }
    private var genresSelected = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource.getInstance()
            )
        presenter = HomePresenter(movieRepository)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
        initTabGenres(genres)
    }

    override fun onGetMoviesNowPlayingSuccess(movies: List<Movie>) {
        movieSlideAdapter.updateData(movies)
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
        exception?.let {
            Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onLoading(isLoad: Boolean) {
        view?.run {
            if (isLoad) {
                homeSwipeRefresh.isRefreshing = false
                frameProgressBarMovie.visibility = View.GONE
            } else {
                frameProgressBarMovie.visibility = View.VISIBLE
            }
        }
    }

    override fun click(item: Movie?) {
        item?.let { addFragment(it) }
    }

    private fun initComponents() {
        initAdapter()
        onLoading(false)
        initPresenter()
        initRefresh()
    }

    private fun initTabGenres(genres: List<Genres>) {
        view?.run {
            if (movieByKeyTabLayout?.tabCount == 0) {
                activity?.let {
                    movieByKeyTabLayout?.setTabTextColors(
                        ContextCompat.getColor(it, R.color.colorTextLightBlue),
                        ContextCompat.getColor(it, R.color.colorOrange)
                    )
                }
                for (element in genres) {
                    movieByKeyTabLayout.addTab(
                        movieByKeyTabLayout.newTab().setText(element.genresName)
                    )
                }
                genresSelected = genres[0].genresID
                presenter.getMovie(
                    Constant.BASE_GENRES_ID,
                    genresSelected.toString()
                )
                movieByKeyTabLayout?.addOnTabSelectedListener(
                    object : TabLayout.OnTabSelectedListener {
                        override fun onTabReselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabUnselected(tab: TabLayout.Tab?) {
                        }

                        override fun onTabSelected(tab: TabLayout.Tab?) {
                            activity?.let {
                                if (NetworkUtil.isConnectedToNetwork(it)) {
                                    tab?.let { genresSelected = genres[it.position].genresID }
                                    presenter.getMovie(
                                        Constant.BASE_GENRES_ID,
                                        genresSelected.toString()
                                    )
                                } else {
                                    onLoading(true)
                                    Toast.makeText(
                                        it,
                                        getString(R.string.check_internet_fail),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
                movieByKeyTabLayout.getTabAt(0)?.select()
            }
        }
    }

    private fun initAdapter() {
        view?.run {
            popularRecyclerView.setHasFixedSize(true)
            popularRecyclerView.adapter = popularAdapter.apply {
                onItemClick = { item, _ ->
                    addFragment(item)
                }
            }
            upcomingRecyclerView.setHasFixedSize(true)
            upcomingRecyclerView.adapter = upcomingAdapter.apply {
                onItemClick = { item, _ ->
                    addFragment(item)
                }
            }
            movieByGenresRecyclerView.setHasFixedSize(true)
            movieByGenresRecyclerView.adapter = movieByIDAdapter.apply {
                onItemClick = { item, _ ->
                    addFragment(item)
                }
            }
            sliderViewPager?.adapter = movieSlideAdapter
            indicatorTabLayout.setupWithViewPager(
                sliderViewPager,
                true
            )
        }
        movieSlideAdapter.setSlideItemClickListener(this)
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    view?.run {
                        if (sliderViewPager.currentItem < movieSlideAdapter.count - 1) {
                            sliderViewPager.currentItem =
                                sliderViewPager.currentItem + 1
                        } else {
                            sliderViewPager.currentItem = 0
                        }
                    }
                }
            }
        }, Constant.BASE_DELAY_SLIDE, Constant.BASE_DELAY_SLIDE)
    }

    private fun initPresenter() {
        presenter.setView(this)
        activity?.let {
            if (NetworkUtil.isConnectedToNetwork(it)) {
                Handler().postDelayed({ presenter.onStart() }, 500)
            } else {
                onLoading(true)
                val message = getString(R.string.check_internet_fail)
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRefresh() {
        view?.homeSwipeRefresh?.setOnRefreshListener {
            activity?.let {
                if (NetworkUtil.isConnectedToNetwork(it)) {
                    presenter.onStart()
                    presenter.getMovie(
                        Constant.BASE_GENRES_ID,
                        genresSelected.toString()
                    )
                } else {
                    onLoading(true)
                    val message = getString(R.string.check_internet_fail)
                    Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFragment(movie: Movie) {
        activity?.let {
            val fragment =
                MovieDetailsFragment.getInstance(movie.movieID, movie.movieTitle)
            if (NetworkUtil.isConnectedToNetwork(it)) {
                it.supportFragmentManager.beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.mainFrameLayout, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                val message = getString(R.string.check_internet_fail)
                Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
