package com.sun_asterisk.moviedb_50.screen.listmovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.model.MovieResultPage
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.details.MovieDetailsFragment
import com.sun_asterisk.moviedb_50.screen.listmovie.adapter.MovieListAdapter
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_list_movie.view.*
import kotlinx.android.synthetic.main.toolbar_base.*

class ListMovieFragment : Fragment(), ListMovieContract.View {
    private lateinit var presenter: ListMovieContract.Presenter
    private val movieListAdapter: MovieListAdapter by lazy { MovieListAdapter() }
    private var totalPage = 0
    private var currentPage = Constant.BASE_PAGE_DEFAULT
    private var isScrolling = false
    private var type: String? = ""
    private var value: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource
            )
        presenter = ListMoviePresenter(movieRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        initPresenter()
        initAdapter()
    }

    override fun onGetMoviesSuccess(movies: List<Movie>) {
        movieListAdapter.insertData(movies)
    }

    override fun onGetMovieResultPage(movieResultPage: MovieResultPage) {
        totalPage = movieResultPage.movieTotalPage
        view?.run {
            resultTextView.text =
                getString(R.string.results_found, movieResultPage.movieTotalResult)

        }
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onLoading(isLoad: Boolean) {
        view?.run {
            if (isLoad) {
                frameProgressBarMovie.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            } else {
                frameProgressBarMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun initPresenter() {
        presenter.setView(this)
        arguments?.run {
            type = getString(Constant.BASE_TYPE)
            value = getString(Constant.BASE_VALUE)
            if (type != null && value != null) {
                activity?.let {
                    if (NetworkUtil.isConnectedToNetwork(it)) {
                        presenter.getMovies(type!!, value!!)
                    } else {
                        val message = getString(R.string.check_internet_fail)
                        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        view?.listMovieRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = movieListAdapter.apply {
                onItemClick = { item, _ ->
                    addFragment(item)
                }
            }
            addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                        isScrolling = true
                }

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager: LinearLayoutManager =
                        layoutManager as LinearLayoutManager
                    if (isScrolling && linearLayoutManager.findLastVisibleItemPosition() ==
                        movieListAdapter.itemCount - 1
                    ) {
                        isScrolling = false
                        if (currentPage + 1 <= totalPage) {
                            activity?.let {
                                if (NetworkUtil.isConnectedToNetwork(it)) {
                                    if (type != null && value != null) {
                                        view?.loadingProgressBar?.visibility = View.VISIBLE
                                        currentPage += 1
                                        presenter.getMovies(type!!, value!!, currentPage)
                                    }
                                } else {
                                    val message = getString(R.string.check_internet_fail)
                                    Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            })
        }
    }

    private fun initToolBar() {
        toolbar_base?.let {
            (activity as? MainActivity)?.run {
                setSupportActionBar(it)
                supportActionBar?.run {
                    setDisplayShowTitleEnabled(true)
                    title = arguments?.getString(Constant.BASE_TITLE)
                }
            }
            it.setNavigationOnClickListener {
                activity?.run { supportFragmentManager.popBackStack() }
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

    companion object {
        fun getInstance(type: String, query: String, title: String) =
            ListMovieFragment().apply {
                arguments = bundleOf(
                    Constant.BASE_TYPE to type,
                    Constant.BASE_VALUE to query,
                    Constant.BASE_TITLE to title
                )
            }
    }
}
