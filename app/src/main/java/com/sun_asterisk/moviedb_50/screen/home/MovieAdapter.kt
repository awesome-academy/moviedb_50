package com.sun_asterisk.moviedb_50.screen.home

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.base.BaseAdapter
import com.sun_asterisk.moviedb_50.base.BaseViewHolder
import com.sun_asterisk.moviedb_50.data.model.Movie
import com.sun_asterisk.moviedb_50.data.source.remote.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter : BaseAdapter<Movie, MovieAdapter.ViewHolder>() {

    var onItemClick: (Movie, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false), onItemClick
    )

    class ViewHolder(
        itemView: View,
        onItemClick: (Movie, Int) -> Unit
    ) : BaseViewHolder<Movie>(itemView, onItemClick) {

        override fun onBindData(itemData: Movie) {
            super.onBindData(itemData)
            getImageCircle(itemData)
            itemView.textImdb.text = itemData.movieVoteAverage.toString()
        }

        private fun getImageCircle(movie: Movie?) {
            GetImageAsyncTask(object : OnFetchImageListener {

                override fun onImageError(e: Exception?) {
                    e?.printStackTrace()
                }

                override fun onImageLoaded(bitmap: Bitmap?) {
                    bitmap?.let { itemView.itemMovieImageView.setImageBitmap(bitmap) }
                }
            }).execute(Constant.BASE_URL_IMAGE + movie?.moviePosterPath)
        }
    }
}
