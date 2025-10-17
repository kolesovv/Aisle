package com.github.kolesovv.aisle.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ItemDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao
}