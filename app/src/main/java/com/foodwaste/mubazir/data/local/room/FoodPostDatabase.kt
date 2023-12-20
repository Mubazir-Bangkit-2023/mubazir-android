package com.foodwaste.mubazir.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foodwaste.mubazir.data.local.entity.FoodPostEntity
import com.foodwaste.mubazir.data.local.entity.RemoteKeys

@Database(
    entities = [FoodPostEntity::class, RemoteKeys::class],
    version = 1
)
abstract class FoodPostDatabase: RoomDatabase() {

    abstract fun postDao(): PostDao

    abstract fun remoteKeysDao(): RemoteKeysDao
}