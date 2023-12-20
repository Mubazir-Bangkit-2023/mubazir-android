package com.foodwaste.mubazir.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class FoodPostEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val distance: Int,
    val lat: Double,
    val lon: Double,
    val categoryId: Int,
)
