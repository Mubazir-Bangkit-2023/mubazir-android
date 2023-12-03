package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        noHp: String,
        password: String
    ): Result<Any> = runCatching {
        userRepository.signUp(name, email, noHp, password)
    }
}