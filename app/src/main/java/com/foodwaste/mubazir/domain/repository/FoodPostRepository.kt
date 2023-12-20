package com.foodwaste.mubazir.domain.repository

import androidx.paging.PagingData
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FoodPostRepository {
    fun browse(lat: Double, lon: Double, search: String?, category: String?, radius: String?, price: String?): Flow<PagingData<FoodPost>>

    suspend fun getDetailPost(id: String): FoodPostDetail

    suspend fun classify(image: MultipartBody.Part): String

    suspend fun postFood(
        token: String,
        title: String,
        categoryId: String,
        freshness: String?,
        price: String,
        pickupTime: String,
        lat: String,
        lon: String,
        description: String,
        image: MultipartBody.Part
    )

    suspend fun getNearbyRecommendation(lat: Double, lon: Double): List<FoodPost>

    suspend fun getNearbyRestaurantRecommendation(lat: Double, lon: Double): List<FoodPost>

    suspend fun getNearbyHomeFoodRecommendation(lat: Double, lon: Double): List<FoodPost>

    suspend fun getNearbyFoodIngredientsRecommendation(lat: Double, lon: Double): List<FoodPost>

    suspend fun getFoodPostMapView(lat: Double, lon: Double): List<FoodPost>

    suspend fun deletePost(token:String, id: String)
}