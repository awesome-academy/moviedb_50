package com.sun_asterisk.moviedb_50.screen.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<T>(itemView: View, private var onItemClick: (T, Int) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    var itemData: T? = null
    private var itemPosition = -1

    init {
        itemView.setOnClickListener {
            itemData?.let { onItemClickListener(it) }
        }
    }

    open fun onBindData(itemData: T) {
        this.itemData = itemData
    }

    open fun onBindData(itemPosition: Int, itemData: T) {
        this.itemPosition = itemPosition
        this.itemData = itemData
    }

    open fun onItemClickListener(itemData: T) = onItemClick(itemData, adapterPosition)
}
