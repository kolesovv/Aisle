package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class UpdateItemUseCase(private val itemRepository: ItemRepository) {

    suspend fun invoke(item: Item) {
        itemRepository.updateItem(item)
    }
}