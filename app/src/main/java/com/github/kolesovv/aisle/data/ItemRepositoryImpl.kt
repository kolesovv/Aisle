package com.github.kolesovv.aisle.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.kolesovv.aisle.data.local.ItemsDatabase
import com.github.kolesovv.aisle.data.local.toDbModel
import com.github.kolesovv.aisle.data.local.toEntities
import com.github.kolesovv.aisle.data.local.toEntity
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class ItemRepositoryImpl private constructor(context: Context) : ItemRepository {

    private val itemsDatabase = ItemsDatabase.getInstance(context)
    private val itemsDao = itemsDatabase.itemsDao()

    override fun getAllItems(): LiveData<List<Item>> {
        return itemsDao.getAllItems().map { it.toEntities() }
    }

    override suspend fun addItem(item: Item) {
        itemsDao.addItem(item.toDbModel())
    }

    override suspend fun deleteItem(itemId: Int) {
        itemsDao.deleteItem(itemId)
    }

    override suspend fun getItem(itemId: Int): Item {
        return itemsDao.getItem(itemId).toEntity()
    }

    override suspend fun updateItem(item: Item) {
        itemsDao.addItem(item.toDbModel())
    }

    override suspend fun switchItemEnableStatus(itemId: Int) {
        itemsDao.switchItemEnableStatus(itemId)
    }

    companion object {

        private var instance: ItemRepositoryImpl? = null
        private val LOCK = Any()

        fun getInstance(context: Context): ItemRepositoryImpl {
            instance?.let { return it }
            synchronized(LOCK) {
                instance?.let { return it }
                return ItemRepositoryImpl(context).also { instance = it }
            }
        }
    }
}