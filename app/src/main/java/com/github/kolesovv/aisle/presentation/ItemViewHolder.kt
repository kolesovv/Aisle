package com.github.kolesovv.aisle.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.kolesovv.aisle.R

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val textViewName = view.findViewById<TextView>(R.id.tv_name)
    val textViewCount = view.findViewById<TextView>(R.id.tv_count)
}
