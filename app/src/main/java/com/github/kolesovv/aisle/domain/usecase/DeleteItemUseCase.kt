package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class DeleteItemUseCase(private val itemRepository: ItemRepository) {

    fun invoke(item: Item) {
        itemRepository.deleteItem(item)
    }
}