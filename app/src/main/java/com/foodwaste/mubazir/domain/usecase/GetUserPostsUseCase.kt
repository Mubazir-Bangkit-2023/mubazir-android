package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class GetUserPostsUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<List<FoodPostDetail>> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        userRepository.getUserPost("Bearer ${user.token}")
    }
}