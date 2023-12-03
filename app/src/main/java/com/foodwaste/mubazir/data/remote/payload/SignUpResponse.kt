package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @field:SerializedName("message")
    val message: String,
)
