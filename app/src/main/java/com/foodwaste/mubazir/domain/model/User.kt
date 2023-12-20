package com.foodwaste.mubazir.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val noHp: String,
    val photo: String?,
    val token: String
)
