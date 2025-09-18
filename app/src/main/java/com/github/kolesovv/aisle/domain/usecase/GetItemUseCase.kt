package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository

class GetItemUseCase(private val itemRepository: ItemRepository) {

    fun invoke(id: Int): Item {
        return itemRepository.getItem(id)
    }
}