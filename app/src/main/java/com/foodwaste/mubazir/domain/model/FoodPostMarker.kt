package com.foodwaste.mubazir.domain.model

data class FoodPostMarker(
    val id: String,
    val title: String,
    val price: Int,
    val categoryId: Int,
    val lat: Double,
    val lon: Double,
    val image: String
)
