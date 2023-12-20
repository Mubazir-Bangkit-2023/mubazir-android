package com.foodwaste.mubazir.data

import androidx.paging.PagingData
import androidx.paging.map
import com.foodwaste.mubazir.data.mapper.toModel
import com.foodwaste.mubazir.data.remote.FoodClassificationRemoteDataSource
import com.foodwaste.mubazir.data.remote.FoodPostRemoteDataSource
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber

class DefaultFoodPostRepository(
    private val foodPostRemoteDataSource: FoodPostRemoteDataSource,
    private val foodClassificationRemoteDataSource: FoodClassificationRemoteDataSource
) : FoodPostRepository {

    override fun browse(lat: Double, lon: Double, search: String?, category: String?, radius: String?, price: String?): Flow<PagingData<FoodPost>> {
        return foodPostRemoteDataSource.browse(lat, lon, search, category, radius, price).map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    override suspend fun getDetailPost(id: String): FoodPostDetail {
        val res = foodPostRemoteDataSource.getDetailPost(id)
        return res.toModel()
    }

    override suspend fun classify(image: MultipartBody.Part): String {
        return foodClassificationRemoteDataSource.classifyImage(image).result
    }

    override suspend fun postFood(
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
    ) {
        val titlePart = title.toRequestBody(MultipartBody.FORM)
        val categoryIdPart = categoryId.toRequestBody(MultipartBody.FORM)
        val freshnessPart = freshness?.toRequestBody(MultipartBody.FORM)
        val pricePart = price.toRequestBody(MultipartBody.FORM)
        val pickupTimePart = pickupTime.toRequestBody(MultipartBody.FORM)
        val latPart = lat.toRequestBody(MultipartBody.FORM)
        val lonPart = lon.toRequestBody(MultipartBody.FORM)
        val descriptionPart = description.toRequestBody(MultipartBody.FORM)

        try {
           foodPostRemoteDataSource.postFood(
                token = token,
                title = titlePart,
                categoryId = categoryIdPart,
                freshness = freshnessPart,
                price = pricePart,
                pickupTime = pickupTimePart,
                lat = latPart,
                lon = lonPart,
                description = descriptionPart,
                file = image
            )
        } catch (e: Exception) {
            Timber.e(e.message ?: "Failed to post food")
            throw Exception("Failed to post food")
        }
    }

    override suspend fun getNearbyRecommendation(lat: Double, lon: Double): List<FoodPost> {
        val res = foodPostRemoteDataSource.getNearbyRecommendation(lat, lon)
        val data = res.map {
            it.toModel()
        }
        return data
    }

    override suspend fun getNearbyRestaurantRecommendation(
        lat: Double,
        lon: Double
    ): List<FoodPost> {
        val res = foodPostRemoteDataSource.getNearbyRestaurantRecommendation(lat, lon)
        val data = res.map {
            it.toModel()
        }
        return data
    }

    override suspend fun getNearbyHomeFoodRecommendation(lat: Double, lon: Double): List<FoodPost> {
        val res = foodPostRemoteDataSource.getNearbyHomeFoodRecommendation(lat, lon)
        val data = res.map {
            it.toModel()
        }
        return data
    }

    override suspend fun getNearbyFoodIngredientsRecommendation(
        lat: Double,
        lon: Double
    ): List<FoodPost> {
        val res = foodPostRemoteDataSource.getNearbyFoodIngredientsRecommendation(lat, lon)
        val data = res.map {
            it.toModel()
        }
        return data
    }

    override suspend fun getFoodPostMapView(lat: Double, lon: Double): List<FoodPost> {
        val res = foodPostRemoteDataSource.getFoodPostMapView(lat, lon)
        val data = res.map {
            it.toModel()
        }
        return data
    }

    override suspend fun deletePost(token: String, id: String) {
        try{
            foodPostRemoteDataSource.deletePost(token, id)
        }catch (e: Exception){
            Timber.d(e)
        }
    }
}