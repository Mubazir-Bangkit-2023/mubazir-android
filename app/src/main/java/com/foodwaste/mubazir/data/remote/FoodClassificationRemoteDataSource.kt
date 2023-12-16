package com.foodwaste.mubazir.data.remote

import com.foodwaste.mubazir.data.remote.payload.FoodClassificationResponse
import com.foodwaste.mubazir.data.remote.service.FoodClassificationService
import okhttp3.MultipartBody

class FoodClassificationRemoteDataSource(
    private val service: FoodClassificationService
) {

    suspend fun classifyImage(image: MultipartBody.Part): FoodClassificationResponse {
        val res = service.classifyImage(image)
        val data = res.takeIf { it.isSuccessful }?.body()

        return data ?: throw RuntimeException("Response body is empty")
    }
}