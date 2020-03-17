package com.sun_asterisk.moviedb_50.screen.details.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.MovieTrailer
import com.sun_asterisk.moviedb_50.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_trailer.view.*

class TrailerAdapter : BaseAdapter<MovieTrailer, TrailerAdapter.ViewHolder>() {

    var onItemClick: (MovieTrailer, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trailer, parent, false), onItemClick
        )

    class ViewHolder(
        itemView: View,
        onItemClick: (MovieTrailer, Int) -> Unit
    ) : BaseViewHolder<MovieTrailer>(itemView, onItemClick) {

        override fun onBindData(itemData: MovieTrailer) {
            super.onBindData(itemData)
            getImageCircle(itemData)
            itemView.trailerTexView.text = itemData.movieTrailerName
        }

        private fun getImageCircle(movieTrailer: MovieTrailer?) {
            GetImageAsyncTask(
                object : OnFetchImageListener {

                    override fun onImageError(e: Exception?) {
                        e?.printStackTrace()
                    }

                    override fun onImageLoaded(bitmap: Bitmap?) {
                        bitmap?.let { itemView.trailerImageView.setImageBitmap(bitmap) }
                    }
                }).execute(Constant.BASE_URL_IMAGE_2 + movieTrailer?.movieTrailerKey + Constant.BASE_URL_IMAGE_DEFAULT)
        }
    }
}
