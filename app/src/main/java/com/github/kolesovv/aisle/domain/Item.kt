package com.github.kolesovv.aisle.domain

data class Item(
    val name: String,
    val count: Int,
    val isEnable: Boolean,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
