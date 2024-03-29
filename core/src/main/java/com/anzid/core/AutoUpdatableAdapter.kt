package com.anzid.core

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Extension function for the adapter that will do most of boilerplate notifyChanges / calculating DiffUtil.
 */
interface AutoUpdatableAdapter {

    fun <T> RecyclerView.Adapter<*>.autoNotify(
        old: List<T>,
        new: List<T>,
        compareItems: (T, T) -> Boolean = { o, n -> o === n },
        compareContents: (T, T) -> Boolean = { o, n -> o == n }
    ) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                compareItems(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                compareContents(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })

        diff.dispatchUpdatesTo(this)
    }
}