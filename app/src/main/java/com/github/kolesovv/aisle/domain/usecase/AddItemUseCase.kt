package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class AddItemUseCase(private val itemRepository: ItemRepository) {

    suspend fun invoke(item: Item) {
        itemRepository.addItem(item)
    }
}