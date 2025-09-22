package com.github.kolesovv.aisle.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.domain.Item

class ShopListAdapter : ListAdapter<Item, ItemViewHolder>(ShopItemDiffCallback()) {

    var onShopItemClickListener: ((itemId: Int) -> Unit)? = null
    var onShopItemLongClickListener: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.textViewName.text = item.name
        holder.textViewCount.text = item.count.toString()
        holder.itemView.setOnClickListener {
            onShopItemClickListener?.invoke(item.id)
        }
        holder.itemView.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.isEnable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLE
        }
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLE = 0

        const val MAX_POOL_SIZE = 15
    }
}
