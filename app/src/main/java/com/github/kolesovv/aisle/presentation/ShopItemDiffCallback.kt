package com.github.kolesovv.aisle.presentation

import androidx.recyclerview.widget.DiffUtil
import com.github.kolesovv.aisle.domain.Item

class ShopItemDiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem == newItem
    }
}
