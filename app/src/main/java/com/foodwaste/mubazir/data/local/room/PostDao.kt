package com.foodwaste.mubazir.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.foodwaste.mubazir.data.local.entity.FoodPostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<FoodPostEntity>)

    @Query("SELECT * FROM post")
    fun pagingSource(): PagingSource<Int, FoodPostEntity>

    @Query("DELETE FROM post")
    suspend fun clearAll()
}