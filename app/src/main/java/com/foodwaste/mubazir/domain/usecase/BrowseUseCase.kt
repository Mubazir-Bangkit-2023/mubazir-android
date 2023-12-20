package com.foodwaste.mubazir.domain.usecase

import androidx.paging.PagingData
import com.foodwaste.mubazir.domain.model.FoodPost
import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import kotlinx.coroutines.flow.Flow

class BrowseUseCase(
    private val foodPostRepository: FoodPostRepository
) {
    operator fun invoke(lat: Double, lon: Double, search: String?, category: String?, radius: String?, price: String?): Flow<PagingData<FoodPost>> = foodPostRepository.browse(lat, lon, search, category, radius, price)
}