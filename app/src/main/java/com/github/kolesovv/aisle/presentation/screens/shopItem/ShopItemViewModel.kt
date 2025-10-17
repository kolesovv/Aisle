package com.github.kolesovv.aisle.presentation.screens.shopItem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kolesovv.aisle.domain.Item
import com.github.kolesovv.aisle.domain.usecase.AddItemUseCase
import com.github.kolesovv.aisle.domain.usecase.GetItemUseCase
import com.github.kolesovv.aisle.domain.usecase.UpdateItemUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopItemViewModel @Inject constructor(
    private val addItemUseCase: AddItemUseCase,
    private val updateItemUseCase: UpdateItemUseCase,
    private val getItemUseCase: GetItemUseCase,
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item>
        get() = _item

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (inputIsValid(name, count)) {
            viewModelScope.launch {
                val item = Item(name, count, true)
                addItemUseCase.invoke(item)
                finishWork()
            }
        }
    }

    fun updateItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (inputIsValid(name, count)) {
            viewModelScope.launch {
                _item.value?.let {
                    val item = it.copy(name = name, count = count)
                    updateItemUseCase.invoke(item)
                    finishWork()
                }
            }
        }
    }

    fun getItem(itemId: Int) {
        viewModelScope.launch {
            val item = getItemUseCase.invoke(itemId)
            _item.value = item
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun inputIsValid(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}
