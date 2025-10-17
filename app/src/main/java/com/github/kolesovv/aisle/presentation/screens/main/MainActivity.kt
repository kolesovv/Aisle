package com.github.kolesovv.aisle.presentation.screens.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.github.kolesovv.aisle.AisleApp
import com.github.kolesovv.aisle.R
import com.github.kolesovv.aisle.presentation.ShopItemFragment
import com.github.kolesovv.aisle.presentation.ShopListAdapter
import com.github.kolesovv.aisle.presentation.ViewModelFactory
import com.github.kolesovv.aisle.presentation.screens.shopItem.ShopItemActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject
import kotlin.getValue

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null
    private val component by lazy {
        (application as AisleApp).component
    }

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(mainActivity = this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewmodelFactory)[MainViewModel::class.java]
        shopItemContainer = findViewById(R.id.shop_item_container)
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.Companion.newIntentAddItem(this)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.Companion.newInstanceAddItem()
                launchFragment(fragment)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        recyclerView.adapter = shopListAdapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.Companion.VIEW_TYPE_ENABLED,
            ShopListAdapter.Companion.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.Companion.VIEW_TYPE_DISABLE,
            ShopListAdapter.Companion.MAX_POOL_SIZE
        )
        setupClickListener()
        setupLongClickListener()
        setupSwipeListener(recyclerView)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val item = shopListAdapter.currentList[viewHolder.bindingAdapterPosition]
                viewModel.deleteItem(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it.id)
        }
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.Companion.newIntentEditItem(this, it)
                startActivity(intent)
            } else {
                val fragment = ShopItemFragment.Companion.newInstanceEditItem(it)
                launchFragment(fragment)
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }
}
