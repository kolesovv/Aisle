package com.github.kolesovv.aisle.domain.usecase

import androidx.lifecycle.LiveData
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.ItemRepository
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(private val itemRepository: ItemRepository) {

    operator fun invoke(): LiveData<List<Item>> {
        return itemRepository.getAllItems()
    }
}