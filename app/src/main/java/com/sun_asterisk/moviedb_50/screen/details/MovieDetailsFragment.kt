package com.sun_asterisk.moviedb_50.screen.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.chip.Chip
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.*
import com.sun_asterisk.moviedb_50.data.repository.MovieRepository
import com.sun_asterisk.moviedb_50.data.source.local.MovieLocalDataSource
import com.sun_asterisk.moviedb_50.data.source.local.dao.FavoritesDaoImpl
import com.sun_asterisk.moviedb_50.data.source.remote.MovieRemoteDataSource
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.details.adapter.CastAdapter
import com.sun_asterisk.moviedb_50.screen.details.adapter.ProduceAdapter
import com.sun_asterisk.moviedb_50.screen.details.adapter.TrailerAdapter
import com.sun_asterisk.moviedb_50.screen.listmovie.ListMovieFragment
import com.sun_asterisk.moviedb_50.utils.*
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.android.synthetic.main.toolbar_base.*
import java.io.ByteArrayOutputStream


class MovieDetailsFragment : Fragment(), MovieDetailsContract.View {
    private lateinit var presenter: MovieDetailsContract.Presenter
    private lateinit var movieFavorite: Movie
    private val castAdapter: CastAdapter by lazy { CastAdapter() }
    private val produceAdapter: ProduceAdapter by lazy { ProduceAdapter() }
    private val trailerAdapter: TrailerAdapter by lazy { TrailerAdapter() }
    private var strMoviePoster: String? = null
    private var byteArrayPoster: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val movieRepository: MovieRepository =
                MovieRepository.getInstance(
                    MovieRemoteDataSource.getInstance(),
                    MovieLocalDataSource.getInstance(FavoritesDaoImpl.getInstance(it))
                )
            presenter = MovieDetailsPresenter(movieRepository)
        }
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
        initFavoriteHandle()
    }

    override fun onGetMovieSuccess(movie: Movie) {
        view?.run {
            voteTextView.text = movie.movieVoteAverage.toString()
            titleTextView.text = movie.movieTitle
            releaseDateTexView.text = movie.movieReleaseDate
            overViewTextView.text = movie.movieOverView
            getImageAsync(movie.movieBackdropPath, backdropImageView)
            getImageAsync(movie.moviePosterPath, posterImageView)
            strMoviePoster = movie.moviePosterPath
            backdropImageView.animation =
                AnimationUtils.loadAnimation(activity, R.anim.scale_animation)
            movieFavorite = movie
        }
    }


    override fun onGetGenresSuccess(genres: List<Genres>) {
        view?.run {
            if (genresChipGroup.childCount == 0) {
                for (item in genres) {
                    val genresChip = LayoutInflater.from(activity)
                        .inflate(R.layout.item_chip, genresChipGroup, false) as Chip
                    genresChip.run {
                        id = item.genresID
                        text = item.genresName
                        setOnCheckedChangeListener { buttonView, isChecked ->
                            if (isChecked) {
                                addFragment(
                                    Constant.BASE_GENRES_ID,
                                    buttonView.id.toString(),
                                    buttonView.text.toString()
                                )
                            }
                        }
                    }
                    genresChipGroup.addView(genresChip)
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

    override fun showFavoriteImage(type: String) {
        if (type == Constant.BASE_NOTIFY_ADD_FAVORITE_SUCCESS) {
            view?.favoriteImageView?.setImageResource(R.drawable.ic_favorite)
        } else {
            view?.favoriteImageView?.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    override fun notifyFavorite(type: String) {
        when (type) {
            Constant.BASE_NOTIFY_ADD_FAVORITE_SUCCESS ->
                activity?.showSnackBar(R.string.notification_add_favorite_success)
            Constant.BASE_NOTIFY_ADD_FAVORITE_ERROR ->
                activity?.showSnackBar(R.string.notification_add_favorite_failed)
            Constant.BASE_NOTIFY_DELETE_FAVORITE_SUCCESS ->
                activity?.showSnackBar(R.string.notification_delete_success)
            Constant.BASE_NOTIFY_DELETE_FAVORITE_ERROR ->
                activity?.showSnackBar(R.string.notification_delete_failed)
        }
    }

    override fun onLoading(isLoad: Boolean) {
        view?.run {
            if (isLoad) {
                detailsSwipeRefresh.isRefreshing = false
                frameProgressBarMovie.visibility = View.GONE
            } else {
                frameProgressBarMovie.visibility = View.VISIBLE
            }
        }
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(activity, it.message.toString(), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun initPresenter() {
        presenter.setView(this)
        activity?.let { activity ->
            if (NetworkUtil.isConnectedToNetwork(activity)) {
                arguments?.let { presenter.getMovieDetails(it.getInt(Constant.BASE_VALUE)) }
            } else {
                onLoading(true)
                val message = getString(R.string.check_internet_fail)
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImageAsync(strUrl: String?, imageView: ImageView) {
        GetImageAsyncTask(
            object : OnFetchImageListener {

                override fun onImageError(e: Exception?) {
                    e?.printStackTrace()
                }

                override fun onImageLoaded(bitmap: Bitmap?) {
                    if (bitmap !== null) {
                        imageView.setImageBitmap(bitmap)
                        if (strUrl == strMoviePoster) {
                            byteArrayPoster = convertBitmapToByteArray(bitmap)
                        }
                    } else {
                        byteArrayPoster = null
                    }
                }
            }).execute(Constant.BASE_URL_IMAGE + strUrl)
    }

    private fun initRefresh() {
        view?.detailsSwipeRefresh?.setOnRefreshListener {
            activity?.let { activity ->
                if (NetworkUtil.isConnectedToNetwork(activity)) {
                    arguments?.let {
                        presenter.getMovieDetails(it.getInt(Constant.BASE_VALUE))
                    }
                } else {
                    onLoading(true)
                    val message = getString(R.string.check_internet_fail)
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initAdapter() {
        view?.run {
            castsRecyclerView.setHasFixedSize(true)
            castsRecyclerView.adapter = castAdapter.apply {
                onItemClick = { cast, _ ->
                    addFragment(Constant.BASE_CAST_ID, cast.castId.toString(), cast.castName)
                }
            }
            producesRecyclerView.setHasFixedSize(true)
            producesRecyclerView.adapter = produceAdapter.apply {
                onItemClick = { produce, _ ->
                    addFragment(
                        Constant.BASE_PRODUCE_ID,
                        produce.produceID.toString(),
                        produce.produceName
                    )
                }
            }
            moviesTrailerRecyclerView.setHasFixedSize(true)
            moviesTrailerRecyclerView.adapter = trailerAdapter.apply {
                onItemClick = { movieTrailer, _ ->
                    watchYoutubeVideo(movieTrailer.movieTrailerKey)
                }
            }
        }
    }

    private fun watchYoutubeVideo(id: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$id"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$id")
        )
        try {
            context?.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context?.startActivity(webIntent)
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

    private fun addFragment(type: String, query: String, title: String) {
        activity?.let {
            val fragment = ListMovieFragment.getInstance(type, query, title)
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

    private fun initFavoriteHandle() {
        view?.favoriteImageView?.setOnClickListener {
            presenter.handleFavorites(Favorite(movieFavorite, byteArrayPoster))
        }
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
        return stream.toByteArray()
    }

    companion object {
        fun getInstance(id: Int, title: String) = MovieDetailsFragment().apply {
            arguments = bundleOf(Constant.BASE_VALUE to id, Constant.BASE_TITLE to title)
        }
    }
}
