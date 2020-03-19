package com.sun_asterisk.moviedb_50.screen.favorite.adapter

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Favorite
import com.sun_asterisk.moviedb_50.screen.base.BaseFilterAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(originItems: MutableList<Favorite>) :
    BaseFilterAdapter<Favorite, FavoriteAdapter.ViewHolder>(originItems) {
    var onItemClick: (Favorite, Int) -> Unit = { _, _ -> }
    var onItemDelete: (Favorite, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(
            itemView,
            onItemClick,
            onItemDelete
        )
    }

    class ViewHolder(
        itemView: View,
        onItemClick: (Favorite, Int) -> Unit,
        private val onItemDelete: (Favorite, Int) -> Unit
    ) : BaseViewHolder<Favorite>(itemView, onItemClick) {

        override fun onBindData(itemData: Favorite) {
            super.onBindData(itemData)
            itemData.moviePosterPath?.let { convertByteArrayToBitMap(itemData.moviePosterPath) }
            itemView.voteRatingBar.rating = itemData.movieVoteAverage.toFloat() * 0.5f
            itemView.titleTextView.text = itemData.movieTitle
            itemView.overviewTextView.text = itemData.movieOverView
            itemView.releaseDateTexView.text = itemData.movieReleaseDate
        }

        init {
            itemView.deleteImageView.setOnClickListener {
                itemData?.let { favorite -> onItemDelete(favorite, adapterPosition) }
            }
        }

        private fun convertByteArrayToBitMap(byteArray: ByteArray) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            itemView.posterImageView.setImageBitmap(bitmap)
        }
    }
}
