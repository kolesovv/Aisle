package com.github.kolesovv.aisle.di

import android.content.Context
import androidx.room.Room
import com.github.kolesovv.aisle.data.ItemRepositoryImpl
import com.github.kolesovv.aisle.data.local.ItemsDao
import com.github.kolesovv.aisle.data.local.ItemsDatabase
import com.github.kolesovv.aisle.domain.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

    companion object {

        @AppScope
        @Provides
        fun provideDatabase(context: Context): ItemsDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = ItemsDatabase::class.java,
                name = "items.db"
            ).fallbackToDestructiveMigration(dropAllTables = true)
                .build()
        }

        @AppScope
        @Provides
        fun provideDao(itemsDatabase: ItemsDatabase): ItemsDao {
            return itemsDatabase.itemsDao()
        }
    }
}