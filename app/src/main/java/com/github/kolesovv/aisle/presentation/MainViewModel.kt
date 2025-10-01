package com.github.kolesovv.aisle.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.github.kolesovv.aisle.data.ItemRepositoryImpl
import com.github.kolesovv.aisle.domain.usecase.DeleteItemUseCase
import com.github.kolesovv.aisle.domain.usecase.GetItemListUseCase
import com.github.kolesovv.aisle.domain.usecase.SwitchItemEnableStatusUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository = ItemRepositoryImpl.getInstance(application)

    private val getItemListUseCase = GetItemListUseCase(itemRepository)
    private val deleteItemUseCase = DeleteItemUseCase(itemRepository)
    private val switchItemEnableStatusUseCase = SwitchItemEnableStatusUseCase(itemRepository)

    val shopList = getItemListUseCase.invoke()

    fun changeEnableState(itemId: Int) {
        viewModelScope.launch {
            switchItemEnableStatusUseCase.invoke(itemId)
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            deleteItemUseCase.invoke(itemId)
        }
    }

}