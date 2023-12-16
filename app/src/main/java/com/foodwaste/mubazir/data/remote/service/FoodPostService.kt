package com.foodwaste.mubazir.data.remote.service

import com.foodwaste.mubazir.data.remote.payload.BrowseResponse
import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodPostService {

    @GET("posts")
    suspend fun getFoodPosts(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ) : Response<BrowseResponse>

    @GET("posts/{id}")
    suspend fun getDetailPost(
        @Path("id") id: String
    ) : Response<FoodPostDetailResponse>

    @POST("posts/food")
    suspend fun postFood(

    )
}