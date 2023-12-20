package com.foodwaste.mubazir.data.remote.payload

import com.google.gson.annotations.SerializedName

data class FoodClassificationResponse(
    @field:SerializedName("result")
    val result: String,
)
