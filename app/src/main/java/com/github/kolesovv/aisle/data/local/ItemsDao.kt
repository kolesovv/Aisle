package com.github.kolesovv.aisle.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemsDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): LiveData<List<ItemDbModel>>

    @Query("SELECT * FROM items WHERE id ==:itemId")
    suspend fun getItem(itemId: Int): ItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(itemDbModel: ItemDbModel)

    @Query("UPDATE items SET isEnable = NOT isEnable WHERE id ==:itemId")
    suspend fun switchItemEnableStatus(itemId: Int)

    @Query("DELETE FROM items WHERE id ==:itemId")
    suspend fun deleteItem(itemId: Int)
}