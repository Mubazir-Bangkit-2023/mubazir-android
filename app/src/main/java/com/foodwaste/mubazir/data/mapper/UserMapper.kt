package com.foodwaste.mubazir.data.mapper

import com.foodwaste.mubazir.data.local.entity.UserEntity
import com.foodwaste.mubazir.data.remote.payload.UserResponse
import com.foodwaste.mubazir.domain.model.User

fun UserResponse.toModel(): User {
    return User(
        id = userId,
        name = name,
        email = email,
        noHp = noHp,
        photo = photo,
        token = token
    )
}

fun UserResponse.toEntity(): UserEntity {
    return UserEntity(
        id = userId,
        name = name,
        email = email,
        noHp = noHp,
        photo = photo,
        token = token
    )
}