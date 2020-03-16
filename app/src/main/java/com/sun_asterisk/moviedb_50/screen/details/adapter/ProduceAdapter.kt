package com.sun_asterisk.moviedb_50.screen.details.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Produce
import com.sun_asterisk.moviedb_50.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import com.sun_asterisk.moviedb_50.utils.Constant
import com.sun_asterisk.moviedb_50.utils.GetImageAsyncTask
import com.sun_asterisk.moviedb_50.utils.OnFetchImageListener
import kotlinx.android.synthetic.main.item_produce.view.*

class ProduceAdapter : BaseAdapter<Produce, ProduceAdapter.ViewHolder>() {

    var onItemClick: (Produce, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_produce, parent, false), onItemClick
        )

    class ViewHolder(
        itemView: View,
        onItemClick: (Produce, Int) -> Unit
    ) : BaseViewHolder<Produce>(itemView, onItemClick) {

        override fun onBindData(itemData: Produce) {
            super.onBindData(itemData)
            getImageCircle(itemData)
            itemView.produceNameTextView.text = itemData.produceName
        }

        private fun getImageCircle(produce: Produce?) {
            GetImageAsyncTask(
                object : OnFetchImageListener {

                    override fun onImageError(e: Exception?) {
                        e?.printStackTrace()
                    }

                    override fun onImageLoaded(bitmap: Bitmap?) {
                        bitmap?.let { itemView.produceProfileImageView.setImageBitmap(bitmap) }
                    }
                }).execute(Constant.BASE_URL_IMAGE + produce?.produceLogo)
        }
    }
}
