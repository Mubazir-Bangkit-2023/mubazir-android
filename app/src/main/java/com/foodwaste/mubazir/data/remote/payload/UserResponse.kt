package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("no_hp")
    val noHp: String,

    @field:SerializedName("photo_url")
    val photo: String?,

    @field:SerializedName("token")
    val token: String
)
