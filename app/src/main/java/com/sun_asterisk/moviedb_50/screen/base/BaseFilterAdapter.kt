package com.sun_asterisk.moviedb_50.screen.base

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

abstract class BaseFilterAdapter<T : BaseFilterAdapter.Searchable, V : BaseViewHolder<T>>(private val originItems:MutableList<T>) :
    RecyclerView.Adapter<V>(), Filterable {

    var items = ArrayList(originItems)
    private var onNothingFound: (() -> Unit)? = null

    override fun getItemCount(): Int = originItems.size

    override fun onBindViewHolder(viewHolder: V, position: Int) {
        getItem(position)?.let { viewHolder.onBindData(it) }
    }

    private fun getItem(position: Int): T? =
        if (position in 0 until itemCount) originItems[position] else null

    fun updateData(newItems: List<T>) {
        originItems.apply {
            if (isNotEmpty()) clear()
            addAll(newItems)
        }
        notifyDataSetChanged()
    }

    fun insertData(insertItems: List<T>) = with(originItems) {
        val firstPosition = size
        addAll(insertItems)
        val secondPosition = size
        notifyItemRangeInserted(firstPosition, secondPosition - 1)
    }

    fun removeData(position: Int) {
        originItems.removeAt(position)
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                originItems.clear()
                if (constraint == null || constraint.isEmpty()) {
                    originItems.addAll(items)
                } else {
                    val searchResults =
                        items.filter {
                            it.getSearchCriteria().toLowerCase()
                                .contains(constraint.toString().toLowerCase().trim())
                        }
                    originItems.addAll(searchResults)

                }
                return filterResults.also {
                    it.values = originItems
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (originItems.isNullOrEmpty())
                    onNothingFound?.invoke()
                notifyDataSetChanged()
            }
        }
    }


    interface Searchable {
        fun getSearchCriteria(): String
    }

    fun search(s: String?, onNothingFound: (() -> Unit)?) {
        this.onNothingFound = onNothingFound
        filter.filter(s)
    }
}
