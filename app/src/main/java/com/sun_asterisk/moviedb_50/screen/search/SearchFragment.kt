package com.sun_asterisk.moviedb_50.screen.search

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.chip.Chip
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.data.model.Genres
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.local.dao.FavoritesDaoImpl
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.details.MovieDetailsFragment
import com.sun_asterisk.moviedb_50.screen.home.adapter.MovieAdapter
import com.sun_asterisk.moviedb_50.screen.listmovie.ListMovieFragment
import com.sun_asterisk.moviedb_50.screen.search.adapter.CategoryAdapter
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.toolbar_base.view.*

class SearchFragment : Fragment(), SearchContract.View {
    private lateinit var presenter: SearchContract.Presenter
    private val topRatedAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val categoryAdapter: CategoryAdapter by lazy { CategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val movieRepository: MovieRepository =
                MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(),
                    MovieLocalDataSource.getInstance(FavoritesDaoImpl.getInstance(it))
                )
            presenter = SearchPresenter(movieRepository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initToolBar()
        initAdapter()
        presenter.setView(this)
        onLoading(false)
        Handler().postDelayed({ presenter.onStart() }, 500)
        initRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_view, menu)
        val searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val fragment = ListMovieFragment.getInstance(
                    Constant.BASE_SEARCH,
                    query,
                    query
                )
                addFragment(fragment)
                return false
            }
        })
    }

    override fun onGetGenresSuccess(genres: List<Genres>) {
        view?.run {
            if (genresChipGroup.childCount == 0) {
                for (item in genres) {
                    val inflate = LayoutInflater.from(activity)
                    val genresChip = inflate.inflate(
                        R.layout.item_chip,
                        genresChipGroup,
                        false
                    ) as Chip
                    genresChip.run {
                        id = item.genresID
                        text = item.genresName
                        setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                val fragment = ListMovieFragment.getInstance(
                                    Constant.BASE_GENRES_ID,
                                    buttonView.id.toString(),
                                    buttonView.text.toString()
                                )
                                addFragment(fragment)
                            }
                        }
                    }
                    genresChipGroup.addView(genresChip)
                }
            }
        }
    }

    override fun getCategoriesSuccess(categories: List<Category>) {
        categoryAdapter.updateData(categories)
    }

    override fun onGetMoviesTopRatedSuccess(movies: List<Movie>) {
        topRatedAdapter.updateData(movies)
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onLoading(isLoad: Boolean) {
        view?.run {
            if (isLoad) {
                searchSwipeRefresh.isRefreshing = false
                frameProgressBarMovie.visibility = View.GONE
            } else {
                frameProgressBarMovie.visibility = View.VISIBLE
            }
        }
    }

    private fun initToolBar() {
        view?.toolbar_base?.let {
            (activity as? MainActivity)?.run { setSupportActionBar(it)
                it.setNavigationOnClickListener {
                    activity?.run { supportFragmentManager.popBackStack() }
                }
            }
        }
    }

    private fun initAdapter() {
        view?.run {
            categoriesRecyclerView.setHasFixedSize(true)
            categoriesRecyclerView.adapter = categoryAdapter.apply {
                onItemClick = { category, _ ->
                    val type =
                        when (category.categoryName) {
                            Category.CategoryEntry.POPULAR -> Constant.BASE_POPULAR
                            Category.CategoryEntry.NOW_PLAYING -> Constant.BASE_NOW_PLAYING
                            Category.CategoryEntry.UPCOMING -> Constant.BASE_UPCOMING
                            Category.CategoryEntry.TOP_RATE -> Constant.BASE_TOP_RATE
                            else -> ""
                        }
                    val fragment = ListMovieFragment.getInstance(
                        type,
                        Constant.BASE_VALUE,
                        category.categoryName
                    )
                    addFragment(fragment)
                }
            }
            topRatedRecyclerView.setHasFixedSize(true)
            topRatedRecyclerView.adapter = topRatedAdapter.apply {
                onItemClick = { movie, _ ->
                    addFragment(MovieDetailsFragment.getInstance(movie.movieID, movie.movieTitle))
                }
            }
        }
    }

    private fun initRefresh() {
        view?.searchSwipeRefresh?.setOnRefreshListener {
            activity?.let {
                if (NetworkUtil.isConnectedToNetwork(it)) {
                    presenter.onStart()
                } else {
                    onLoading(true)
                    val message = getString(R.string.check_internet_fail)
                    Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        activity?.let {
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
