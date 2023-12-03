package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @field:SerializedName("fullname")
    val name : String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("no_hp")
    val noHp : String,

    @field:SerializedName("password")
    val password: String
)
