package com.github.kolesovv.aisle.data.local

import com.github.kolesovv.aisle.domain.Item

fun Item.toDbModel(): ItemDbModel {
    return ItemDbModel(
        name = name,
        count = count,
        isEnable = isEnable,
        id = id
    )
}

fun ItemDbModel.toEntity(): Item {
    return Item(
        name = name,
        count = count,
        isEnable = isEnable,
        id = id
    )
}

fun List<ItemDbModel>.toEntities(): List<Item> {
    return map { it.toEntity() }
}