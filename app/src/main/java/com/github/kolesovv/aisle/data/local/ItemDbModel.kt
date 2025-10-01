package com.github.kolesovv.aisle.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemDbModel(
    val name: String,
    val count: Int,
    val isEnable: Boolean,

    @PrimaryKey(autoGenerate = true)
    val id: Int
)