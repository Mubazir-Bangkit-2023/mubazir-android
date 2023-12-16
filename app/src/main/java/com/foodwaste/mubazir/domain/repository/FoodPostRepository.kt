package com.foodwaste.mubazir.domain.repository

import androidx.paging.PagingData
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FoodPostRepository {
    fun browse(lat: Double, lon: Double): Flow<PagingData<FoodPost>>

    suspend fun getDetailPost(id: String): FoodPostDetail

    suspend fun classify(image: MultipartBody.Part): String
}