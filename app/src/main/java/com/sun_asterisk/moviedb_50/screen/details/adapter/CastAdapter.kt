package com.sun_asterisk.moviedb_50.screen.details.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Cast
import com.sun_asterisk.moviedb_50.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter : BaseAdapter<Cast, CastAdapter.ViewHolder>() {

    var onItemClick: (Cast, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cast, parent, false), onItemClick
        )

    class ViewHolder(
        itemView: View,
        onItemClick: (Cast, Int) -> Unit
    ) : BaseViewHolder<Cast>(itemView, onItemClick) {

        override fun onBindData(itemData: Cast) {
            super.onBindData(itemData)
            getImageCircle(itemData)
            itemView.castNameTextView.text = itemData.castName
        }

        private fun getImageCircle(cast: Cast?) {
            GetImageAsyncTask(
                object : OnFetchImageListener {

                    override fun onImageError(e: Exception?) {
                        e?.printStackTrace()
                    }

                    override fun onImageLoaded(bitmap: Bitmap?) {
                        bitmap?.let { itemView.castProfileImageView.setImageBitmap(bitmap) }
                    }
                }).execute(Constant.BASE_URL_IMAGE + cast?.castProfilePath)
        }
    }
}
