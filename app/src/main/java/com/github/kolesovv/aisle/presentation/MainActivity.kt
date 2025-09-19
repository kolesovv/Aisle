package com.github.kolesovv.aisle.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.domain.Item

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        llShopList = findViewById(R.id.ll_shop_list)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showList(list: List<Item>) {
        llShopList.removeAllViews()
        for (item in list) {
            val cardLayoutId = if (item.isEnable) {
                R.layout.item_shop_disabled
            } else {
                R.layout.item_shop_enabled
            }
            val view = LayoutInflater.from(this).inflate(
                cardLayoutId,
                llShopList,
                false
            )
            val textViewName = view.findViewById<TextView>(R.id.tv_name)
            val textViewCount = view.findViewById<TextView>(R.id.tv_count)
            textViewName.text = item.name
            textViewCount.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnableState(item)
                true
            }
            llShopList.addView(view)
        }
    }
}