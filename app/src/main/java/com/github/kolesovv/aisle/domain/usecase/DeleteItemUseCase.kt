package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.ItemRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(itemId: Int) {
        itemRepository.deleteItem(itemId)
    }
}