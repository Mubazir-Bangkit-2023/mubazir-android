package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.model.User
import com.foodwaste.mubazir.domain.repository.UserRepository

class SignInUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> = runCatching {
        userRepository.signIn(email, password)
    }
}