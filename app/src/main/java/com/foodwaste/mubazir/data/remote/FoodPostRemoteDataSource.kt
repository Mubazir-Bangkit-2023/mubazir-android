package com.foodwaste.mubazir.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.foodwaste.mubazir.data.local.entity.FoodPostEntity
import com.foodwaste.mubazir.data.local.room.FoodPostDatabase
import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import com.foodwaste.mubazir.data.remote.payload.FoodPostResponse
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.data.remote.service.FoodPostService
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class FoodPostRemoteDataSource(
    private val foodPostService: FoodPostService,
    private val foodPostDatabase: FoodPostDatabase
) {
    fun browse(lat: Double, lon: Double, search: String?, category: String?, radius: String?, price: String?): Flow<PagingData<FoodPostEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            remoteMediator = FoodPostRemoteMediator(
                lat = lat,
                lon = lon,
                search = search,
                category = category,
                radius = radius,
                price = price,
                foodPostService = foodPostService,
                foodPostDatabase = foodPostDatabase
            ),
            pagingSourceFactory = {
                foodPostDatabase.postDao().pagingSource()
            }
        ).flow

    }

    suspend fun getDetailPost(id: String): FoodPostDetailResponse {
        val res = foodPostService.getDetailPost(id)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun postFood(
        token: String,
        title: RequestBody,
        categoryId: RequestBody,
        freshness: RequestBody?,
        price: RequestBody,
        pickupTime: RequestBody,
        lat: RequestBody,
        lon: RequestBody,
        description: RequestBody,
        file: MultipartBody.Part
    ) : MessageResponse {

            val res = foodPostService.postFood(
                token = token,
                title = title,
                categoryId = categoryId,
                freshness = freshness,
                price = price,
                pickupTime = pickupTime,
                lat = lat,
                lon = lon,
                description = description,
                file = file
            )
            val data = res.takeIf { it.isSuccessful }?.body()
            if (data == null) {
                res.errorBody()?.let {
                    throw RuntimeException("Error: ${it.getErrorMessage()}")
                }
            }
            return data ?: throw RuntimeException("Response body is empty")
    }


    suspend fun getNearbyRecommendation(lat: Double, lon: Double): List<FoodPostResponse> {
        val res = foodPostService.getNearbyRecommendation(lat, lon)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun getNearbyRestaurantRecommendation(lat: Double, lon: Double): List<FoodPostResponse> {
        val res = foodPostService.getNearbyRestaurantRecommendation(lat, lon)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun getNearbyHomeFoodRecommendation(lat: Double, lon: Double): List<FoodPostResponse> {
        val res = foodPostService.getNearbyHomefoodRecommendation(lat, lon)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun getNearbyFoodIngredientsRecommendation(lat: Double, lon: Double): List<FoodPostResponse> {
        val res = foodPostService.getNearbyFoodIngredientsRecommendation(lat, lon)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun getFoodPostMapView(lat: Double, lon: Double): List<FoodPostResponse> {
        val res = foodPostService.getFoodPosts(lat, lon, page = null, limit = 100, search = null, category = null, radius = null, price = null)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(it.getErrorMessage())
            }
        }
        return data ?: throw RuntimeException("Unknown error")
    }

    suspend fun deletePost(token: String, id: String) {
        try {
            foodPostService.deletePost(token, id)
        }catch (e: Exception){
            Timber.d(e)
            throw Exception(e.message)
        }
    }
}