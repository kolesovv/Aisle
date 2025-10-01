package com.github.kolesovv.aisle.domain

import androidx.lifecycle.LiveData

interface ItemRepository {

    fun getAllItems(): LiveData<List<Item>>

    suspend fun addItem(item: Item)

    suspend fun deleteItem(itemId: Int)

    suspend fun getItem(itemId: Int): Item

    suspend fun updateItem(item: Item)

    suspend fun switchItemEnableStatus(itemId: Int)
}