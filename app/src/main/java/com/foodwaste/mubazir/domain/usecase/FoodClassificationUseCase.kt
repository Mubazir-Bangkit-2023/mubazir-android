package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import okhttp3.MultipartBody

class FoodClassificationUseCase(
    private val foodPostRepository: FoodPostRepository
) {
    suspend operator fun invoke(image: MultipartBody.Part): Result<String> = runCatching {
        foodPostRepository.classify(image)
    }
}