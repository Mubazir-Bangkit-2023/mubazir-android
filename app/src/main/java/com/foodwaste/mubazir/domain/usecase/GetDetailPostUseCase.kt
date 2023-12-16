package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.repository.FoodPostRepository

class GetDetailPostUseCase(
    private val repository: FoodPostRepository
) {
    suspend operator fun invoke(id: String): Result<FoodPostDetail> = runCatching {
        repository.getDetailPost(id)
    }
}