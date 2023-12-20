package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class UserPostResponse(
    @field:SerializedName("posts")
    val posts: List<FoodPostDetailResponse>
)
