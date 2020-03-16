package com.sun_asterisk.moviedb_50.screen.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.*
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.details.adapter.CastAdapter
import com.sun_asterisk.moviedb_50.screen.details.adapter.ProduceAdapter
import com.sun_asterisk.moviedb_50.screen.details.adapter.TrailerAdapter
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.NetworkUtil
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.fragment_movie_details.view.*

class MovieDetailsFragment : Fragment(), MovieDetailsContract.View {
    private lateinit var presenter: MovieDetailsContract.Presenter
    private val castAdapter: CastAdapter by lazy { CastAdapter() }
    private val produceAdapter: ProduceAdapter by lazy { ProduceAdapter() }
    private val trailerAdapter: TrailerAdapter by lazy { TrailerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movieRepository: MovieRepository =
            MovieRepository.getInstance(
                MovieRemoteDataSource.getInstance(),
                MovieLocalDataSource
            )
        presenter = MovieDetailsPresenter(movieRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolBar()
        initPresenter()
        initRefresh()
        initAdapter()
    }

    override fun onGetMovieSuccess(movie: Movie) {
        view?.run {
            voteTextView.text = movie.movieVoteAverage.toString()
            titleTextView.text = movie.movieTitle
            releaseDateTexView.text = movie.movieReleaseDate
            overViewTextView.text = movie.movieOverView
            getImageAsync(movie.movieBackdropPath, backdropImageView)
            getImageAsync(movie.moviePosterPath, posterImageView)
            backdropImageView.animation = AnimationUtils.loadAnimation(activity, R.anim.scale_animation)
        }
    }


    override fun onGetGenresSuccess(genres: List<Genres>) {
        view?.run {
            if (genresChipGroup.childCount == 0) {
                for (item in genres) {
                    val chip = LayoutInflater.from(activity)
                        .inflate(R.layout.item_chip, genresChipGroup, false) as Chip
                    chip.run {
                        id = item.genresID
                        text = item.genresName
                        setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                            }
                        }
                    }
                    genresChipGroup.addView(chip)
                }
            }
        }
    }

    override fun onGetCastsSuccess(casts: List<Cast>) {
        castAdapter.updateData(casts)
    }

    override fun onGetProducesSuccess(produces: List<Produce>) {
        produceAdapter.updateData(produces)
    }

    override fun onGetMovieTrailerSuccess(movieTrailers: List<MovieTrailer>) {
        trailerAdapter.updateData(movieTrailers)
    }

    override fun onLoading(isLoad: Boolean) {
        view?.run {
            when (isLoad) {
                false -> detailsProgressBarLayout.visibility = View.VISIBLE
                true -> {
                    detailsSwipeRefresh.isRefreshing = false
                    detailsProgressBarLayout.visibility = View.GONE
                }
            }
        }
    }

    override fun onError(exception: Exception?) {
        Toast.makeText(activity, exception?.message.toString(), Toast.LENGTH_LONG)
            .show()
    }

    private fun initPresenter() {
        presenter.setView(this)
        if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
            arguments?.let { presenter.getMovieDetails(it.getInt(Constant.BASE_VALUE)) }
        } else {
            onLoading(true)
            Toast.makeText(activity, getString(R.string.check_internet_fail), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun getImageAsync(strUrl: String?, imageView: ImageView) {
        GetImageAsyncTask(
            object : OnFetchImageListener {

                override fun onImageError(e: Exception?) {
                    e?.printStackTrace()
                }

                override fun onImageLoaded(bitmap: Bitmap?) {
                    bitmap?.let { imageView.setImageBitmap(bitmap) }
                }
            }).execute(Constant.BASE_URL_IMAGE + strUrl)
    }

    private fun initRefresh() {
        view?.detailsSwipeRefresh?.setOnRefreshListener {
            if (activity?.let { NetworkUtil.isConnectedToNetwork(it) } == true) {
                arguments?.let {
                    presenter.getMovieDetails(it.getInt(Constant.BASE_VALUE))
                }
            } else {
                onLoading(true)
                Toast.makeText(activity, getString(R.string.check_internet_fail), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun initAdapter() {
        view?.run {
            castsRecyclerView.setHasFixedSize(true)
            castsRecyclerView.adapter = castAdapter.apply {
                onItemClick = { item, _ ->
                }
            }
            producesRecyclerView.setHasFixedSize(true)
            producesRecyclerView.adapter = produceAdapter.apply {
                onItemClick = { item, _ ->
                }
            }
            moviesTrailerRecyclerView.setHasFixedSize(true)
            moviesTrailerRecyclerView.adapter = trailerAdapter.apply {
                onItemClick = { item, _ ->
                }
            }
        }
    }

    private fun initToolBar() {
        view?.let {
            val toolbar = it.findViewById<Toolbar>(R.id.toolbar)
            (activity as MainActivity).run {
                setSupportActionBar(toolbar)
                supportActionBar?.run {
                    setDisplayShowTitleEnabled(true)
                    title = arguments?.getString(Constant.BASE_TITLE)
                }
            }
            toolbar.setNavigationOnClickListener {
                activity?.run { supportFragmentManager.popBackStack() }
            }
        }
    }

    companion object {
        fun getInstance(id: Int, title: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(Constant.BASE_VALUE to id, Constant.BASE_TITLE to title)
        }
    }
}
