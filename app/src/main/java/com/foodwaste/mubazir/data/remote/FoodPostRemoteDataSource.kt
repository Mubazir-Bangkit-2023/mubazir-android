package com.foodwaste.mubazir.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.foodwaste.mubazir.data.local.entity.FoodPostEntity
import com.foodwaste.mubazir.data.local.room.FoodPostDatabase
import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import com.foodwaste.mubazir.data.remote.service.FoodPostService
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class FoodPostRemoteDataSource(
    private val foodPostService: FoodPostService,
    private val foodPostDatabase: FoodPostDatabase
) {
    fun browse(lat: Double, lon: Double): Flow<PagingData<FoodPostEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            remoteMediator = FoodPostRemoteMediator(lat = lat, lon = lon, foodPostService = foodPostService, foodPostDatabase = foodPostDatabase),
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
}