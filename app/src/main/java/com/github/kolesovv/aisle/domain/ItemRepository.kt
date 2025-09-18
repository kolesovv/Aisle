package com.github.kolesovv.aisle.domain

import androidx.lifecycle.LiveData

interface ItemRepository {

    fun addItem(item: Item)

    fun deleteItem(item: Item)

    fun getAllItems(): LiveData<List<Item>>

    fun getItem(id: Int): Item

    fun updateItem(item: Item)
}