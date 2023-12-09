package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class Api<T>(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: T
)
