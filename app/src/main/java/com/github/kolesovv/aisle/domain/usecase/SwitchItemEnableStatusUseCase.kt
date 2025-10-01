package com.github.kolesovv.aisle.domain.usecase

import com.github.kolesovv.aisle.domain.ItemRepository

class SwitchItemEnableStatusUseCase(private val itemRepository: ItemRepository) {

    suspend fun invoke(itemId: Int) {
        itemRepository.switchItemEnableStatus(itemId)
    }
}