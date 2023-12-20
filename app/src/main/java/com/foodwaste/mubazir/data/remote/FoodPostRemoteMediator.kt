package com.foodwaste.mubazir.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.foodwaste.mubazir.data.local.entity.FoodPostEntity
import com.foodwaste.mubazir.data.local.entity.RemoteKeys
import com.foodwaste.mubazir.data.local.room.FoodPostDatabase
import com.foodwaste.mubazir.data.mapper.toEntity
import com.foodwaste.mubazir.data.remote.service.FoodPostService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class FoodPostRemoteMediator(
    private val lat: Double,
    private val lon: Double,
    private val search: String?,
    private val category: String?,
    private val radius: String?,
    private val price: String?,
    private val foodPostService: FoodPostService,
    private val foodPostDatabase: FoodPostDatabase
): RemoteMediator<Int, FoodPostEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FoodPostEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = foodPostService.getFoodPosts(
                page = page,
                limit = state.config.pageSize,
                lat = lat,
                lon = lon,
                search = search,
                category = category,
                radius = radius,
                price = price,
            )
            val data = response.body()?.posts ?: emptyList()
            val endOfPaginationReached = data.isEmpty()

            foodPostDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    foodPostDatabase.remoteKeysDao().deleteRemoteKeys()
                    foodPostDatabase.postDao().clearAll()
                }
                val prevKey = if(page == 1) null else page - 1
                val nextKey = if(endOfPaginationReached) null else page + 1
                val keys = data.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                foodPostDatabase.remoteKeysDao().insertAll(keys)

                val foodPosts = data.map { foodPostResponse ->
                    foodPostResponse.toEntity()
                }
                foodPostDatabase.postDao().insertAll(foodPosts)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, FoodPostEntity>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
                foodPostDatabase.remoteKeysDao().getRemoteKeysId(data.id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, FoodPostEntity>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
                foodPostDatabase.remoteKeysDao().getRemoteKeysId(data.id)
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, FoodPostEntity>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let { id ->
                    foodPostDatabase.remoteKeysDao().getRemoteKeysId(id)
                }
            }
        }
    }


}