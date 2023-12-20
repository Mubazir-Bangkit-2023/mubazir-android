package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @field:SerializedName("message")
    val message: String,
)
