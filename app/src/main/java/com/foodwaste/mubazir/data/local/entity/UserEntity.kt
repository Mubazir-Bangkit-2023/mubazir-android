package com.foodwaste.mubazir.data.local.entity

data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val noHp: String,
    val photo: String?,
    val token: String
)
