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

class DefaultFoodPostRepository(
    private val foodPostRemoteDataSource: FoodPostRemoteDataSource,
    private val foodClassificationRemoteDataSource: FoodClassificationRemoteDataSource
) : FoodPostRepository {

    override fun browse(lat: Double, lon: Double): Flow<PagingData<FoodPost>> {
        return foodPostRemoteDataSource.browse(lat, lon).map { pagingData ->
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
}