package com.sun_asterisk.moviedb_50.screen.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.data.model.Category
import com.sun_asterisk.moviedb_50.screen.base.BaseAdapter
import com.sun_asterisk.moviedb_50.screen.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter : BaseAdapter<Category, CategoryAdapter.ViewHolder>() {
    var onItemClick: (Category, Int) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false), onItemClick
        )

    class ViewHolder(
        itemView: View,
        onItemClick: (Category, Int) -> Unit
    ) : BaseViewHolder<Category>(itemView, onItemClick) {

        override fun onBindData(itemData: Category) {
            super.onBindData(itemData)
            itemView.categoryNameTextView.text = itemData.categoryName
            itemView.categoryImageView.setImageResource(itemData.categoryImage)
        }
    }
}
