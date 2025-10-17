package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {

    suspend fun invoke(item: Item) {
        itemRepository.addItem(item)
    }
}