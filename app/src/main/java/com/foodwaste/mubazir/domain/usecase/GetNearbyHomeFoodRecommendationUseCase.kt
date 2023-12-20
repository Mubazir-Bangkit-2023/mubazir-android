package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.FoodPostRepository

class GetNearbyHomeFoodRecommendationUseCase(
    private val foodPostRepository: FoodPostRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = foodPostRepository.getNearbyHomeFoodRecommendation(lat, lon)
}
