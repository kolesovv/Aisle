package com.github.kolesovv.aisle.presentation

import androidx.lifecycle.ViewModel
import com.github.kolesovv.aisle.data.ItemRepositoryImpl
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.usecase.DeleteItemUseCase
import com.github.kolesovv.aisle.domain.usecase.GetItemListUseCase
import com.github.kolesovv.aisle.domain.usecase.UpdateItemUseCase

class MainViewModel : ViewModel() {

    private val itemRepository = ItemRepositoryImpl

    private val getItemListUseCase = GetItemListUseCase(itemRepository)
    private val updateItemUseCase = UpdateItemUseCase(itemRepository)
    private val deleteItemUseCase = DeleteItemUseCase(itemRepository)

    val shopList = getItemListUseCase.invoke()

    fun changeEnableState(item: Item) {
        val newItem = item.copy(isEnable = !item.isEnable)
        updateItemUseCase.invoke(newItem)
    }

    fun deleteItem(item: Item) {
        deleteItemUseCase.invoke(item)
    }
}