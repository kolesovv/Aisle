package com.github.kolesovv.aisle.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class ItemRepositoryImpl : ItemRepository {

    private val liveData = MutableLiveData<List<Item>>()
    private val shopList = sortedSetOf<Item>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId: Int = 0

    init {
        repeat(10) {
            addItem(
                Item(
                    name = "Product $it",
                    count = 0,
                    isEnable = false
                )
            )
        }
    }

    override fun addItem(item: Item) {
        if (item.id == Item.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
        updateList()
    }

    override fun deleteItem(item: Item) {
        shopList.remove(item)
        updateList()
    }

    override fun getAllItems(): LiveData<List<Item>> {
        return liveData
    }

    override fun getItem(id: Int): Item {
        return shopList.find { it.id == id }
            ?: throw RuntimeException("Element with id $id not found")
    }

    override fun updateItem(item: Item) {
        val oldItem = getItem(item.id)
        deleteItem(oldItem)
        addItem(item)
        updateList()
    }

    private fun updateList() {
        liveData.value = shopList.toList()
    }
}