package com.foodwaste.mubazir.domain.model

data class FoodPostDetail(
    val id: String,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val freshness: String?,
    val pickupTime: Long,
    val lat: Double,
    val lon: Double,
    val categoryId: Int,
    val createdAt: Long,
    val user: UserInfo
)

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val noHp: String,
    val photo: String?,
)
