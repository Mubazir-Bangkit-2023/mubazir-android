package com.foodwaste.mubazir.data.remote.service

import com.foodwaste.mubazir.data.remote.payload.FoodClassificationResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FoodClassificationService {

    @Multipart
    @POST("predict_image")
    suspend fun classifyImage(
        @Part file: MultipartBody.Part
    ) : Response<FoodClassificationResponse>
}