package com.foodwaste.mubazir.domain.usecase

import android.location.Location
import com.foodwaste.mubazir.domain.repository.UserRepository

class SetStoredLocationUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(location: Location) {
        userRepository.setStoredLocation(location)
    }
}