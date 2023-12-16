package com.foodwaste.mubazir.data.mapper

import com.foodwaste.mubazir.data.local.entity.FoodPostEntity
import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import com.foodwaste.mubazir.data.remote.payload.FoodPostResponse
import com.foodwaste.mubazir.data.remote.payload.User
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.model.UserInfo

fun FoodPostResponse.toEntity(): FoodPostEntity {
    return FoodPostEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imgUrl,
        distance = distance,
        lon = lon,
        lat = lat,
        categoryId = categoryId,
    )
}

fun FoodPostEntity.toModel(): FoodPost {
    return FoodPost(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imageUrl,
        distance = distance,
        lon = lon,
        lat = lat,
        categoryId = categoryId,
    )
}

fun FoodPostDetailResponse.toModel(): FoodPostDetail {
    return FoodPostDetail(
        id = id,
        title = title,
        description = description,
        price = price,
        imageUrl = imgUrl,
        freshness = freshness,
        pickupTime = pickupTime,
        lat = lat,
        lon = lon,
        categoryId = categoryId,
        createdAt = createdAt,
        user = user.toModel()
    )
}

fun User.toModel(): UserInfo {
    return UserInfo(
        id = id,
        name = fullname,
        email = email,
        noHp = noHp,
        photo = photoUrl,
    )
}