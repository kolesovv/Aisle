package com.github.kolesovv.aisle.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.kolesovv.aisle.data.local.ItemsDao
import com.github.kolesovv.aisle.data.local.toDbModel
import com.github.kolesovv.aisle.data.local.toEntities
import com.github.kolesovv.aisle.data.local.toEntity
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val itemsDao: ItemsDao) : ItemRepository {

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
}