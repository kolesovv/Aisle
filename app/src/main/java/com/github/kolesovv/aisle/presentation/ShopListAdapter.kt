package com.github.kolesovv.aisle.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.domain.Item

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ItemViewHolder>() {

    var shopList = listOf<Item>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var count = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        Log.d("ShopListAdapter", "count: ${count++}")
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
        val item = shopList[position]
        holder.textViewName.text = item.name
        holder.textViewCount.text = item.count.toString()
        holder.itemView.setOnLongClickListener {
            true
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.isEnable) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLE
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewName = view.findViewById<TextView>(R.id.tv_name)
        val textViewCount = view.findViewById<TextView>(R.id.tv_count)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLE = 0

        const val MAX_POOL_SIZE = 15
    }
}
