package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository
) {

        suspend operator fun invoke() = runCatching {
            userRepository.signOut()
        }
}