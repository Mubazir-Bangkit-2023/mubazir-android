package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.UserRepository

class GetStoredLocationUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getStoredLocation()
}