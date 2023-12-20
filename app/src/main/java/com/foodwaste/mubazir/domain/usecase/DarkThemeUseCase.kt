package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DarkThemeUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(isDarkTheme: Boolean?) {
        userRepository.setDarkTheme(isDarkTheme)
    }

    operator fun invoke(): Flow<Boolean?> {
        return userRepository.getDarkTheme()
    }
}