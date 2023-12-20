package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.model.User
import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userRepository: UserRepository
) {

        operator fun invoke(): Flow<User?> {
               return userRepository.getUser()
        }
}