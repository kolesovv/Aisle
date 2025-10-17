package com.github.kolesovv.aisle.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kolesovv.aisle.domain.usecase.DeleteItemUseCase
import com.github.kolesovv.aisle.domain.usecase.GetItemListUseCase
import com.github.kolesovv.aisle.domain.usecase.SwitchItemEnableStatusUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val switchItemEnableStatusUseCase: SwitchItemEnableStatusUseCase
) : ViewModel() {
    val shopList = getItemListUseCase()

    fun changeEnableState(itemId: Int) {
        viewModelScope.launch {
            switchItemEnableStatusUseCase(itemId)
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            deleteItemUseCase(itemId)
        }
    }
}