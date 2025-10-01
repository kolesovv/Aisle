package com.github.kolesovv.aisle.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ItemDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {

        private var instance: ItemsDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): ItemsDatabase {

            instance?.let { return it }

            synchronized(LOCK) {
                instance?.let { return it }

                return Room.databaseBuilder(
                    context = context,
                    klass = ItemsDatabase::class.java,
                    name = "items.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}