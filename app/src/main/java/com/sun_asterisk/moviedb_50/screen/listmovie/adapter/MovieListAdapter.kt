package com.sun_asterisk.moviedb_50.screen.listmovie.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_lis_movie.view.*

class MovieListAdapter : BaseAdapter<Movie, MovieListAdapter.ViewHolder>() {
    var onItemClick: (Movie, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lis_movie, parent, false)
        return ViewHolder(itemView, onItemClick)
    }

    class ViewHolder(
        itemView: View,
        onItemClick: (Movie, Int) -> Unit
    ) : BaseViewHolder<Movie>(itemView, onItemClick) {

        override fun onBindData(itemData: Movie) {
            super.onBindData(itemData)
            getImageCircle(itemData)
            itemView.voteRatingBar.rating = (itemData.movieVoteAverage * 0.5f).toFloat()
            itemView.titleTextView.text = itemData.movieTitle
            itemView.overviewTextView.text = itemData.movieOverView
            itemView.releaseDateTexView.text = itemData.movieReleaseDate
        }

        private fun getImageCircle(movie: Movie?) {
            GetImageAsyncTask(object : OnFetchImageListener {

                override fun onImageError(e: Exception?) {
                    e?.printStackTrace()
                }

                override fun onImageLoaded(bitmap: Bitmap?) {
                    bitmap?.let { itemView.posterImageView.setImageBitmap(bitmap) }
                }
            }).execute(Constant.BASE_URL_IMAGE + movie?.moviePosterPath)
        }
    }
}
