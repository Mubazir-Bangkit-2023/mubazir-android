package com.foodwaste.mubazir.domain.model


data class FoodPost(
    val id: String,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val distance: Int,
    val lat: Double,
    val lon: Double,
    val categoryId: Int,
)
