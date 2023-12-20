package com.foodwaste.mubazir.data.remote.service

import com.foodwaste.mubazir.data.remote.payload.BrowseResponse
import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodPostService {

    @GET("posts")
    suspend fun getFoodPosts(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
        @Query("search") search: String?,
        @Query("category") category: String?,
        @Query("radius") radius: String?,
        @Query("price") price: String?,
    ) : Response<BrowseResponse>

    @GET("posts/{id}")
    suspend fun getDetailPost(
        @Path("id") id: String
    ) : Response<FoodPostDetailResponse>

    @Multipart
    @POST("posts/food")
    suspend fun postFood(
        @Header("Authorization") token: String,
        @Part("title") title: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("freshness") freshness: RequestBody?,
        @Part("price") price: RequestBody,
        @Part("pickupTime") pickupTime: RequestBody,
        @Part("lat") lat: RequestBody,
        @Part("lon") lon: RequestBody,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ) : Response<MessageResponse>

    @GET("recommendation/nearby")
    suspend fun getNearbyRecommendation(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
    ) : Response<BrowseResponse>

    @GET("recommendation/restaurant")
    suspend fun getNearbyRestaurantRecommendation(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
    ) : Response<BrowseResponse>

    @GET("recommendation/homefood")
    suspend fun getNearbyHomefoodRecommendation(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
    ) : Response<BrowseResponse>

    @GET("recommendation/rawIngredients")
    suspend fun getNearbyFoodIngredientsRecommendation(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
    ) : Response<BrowseResponse>

    @DELETE("posts/delete/{id}")
    suspend fun deletePost(
        @Header("Authorization") token: String,
        @Path("id") id: String
    )

}