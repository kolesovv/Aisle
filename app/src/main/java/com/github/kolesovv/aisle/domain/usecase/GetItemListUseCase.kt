package com.github.kolesovv.aisle.domain.usecase

import androidx.lifecycle.LiveData
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class GetItemListUseCase(private val itemRepository: ItemRepository) {

    fun invoke(): LiveData<List<Item>> {
        return itemRepository.getAllItems()
    }
}