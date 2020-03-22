package com.sun_asterisk.moviedb_50.screen.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, V : BaseViewHolder<T>> : RecyclerView.Adapter<V>() {

    private val items = ArrayList<T>()

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: V, position: Int) {
        getItem(position)?.let { viewHolder.onBindData(it) }
    }

    private fun getItem(position: Int): T? =
        if (position in 0 until itemCount) items[position] else null

    fun updateData(newItems: List<T>) {
        items.apply {
            if (isNotEmpty()) clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    open fun getItems(): List<T> {
        return items
    }

    fun insertData(insertItems: List<T>) = with(items) {
        val firstPosition = size
        addAll(insertItems)
        val secondPosition = size
        notifyItemRangeInserted(firstPosition, secondPosition - 1)
    }

    fun removeData(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }
}
