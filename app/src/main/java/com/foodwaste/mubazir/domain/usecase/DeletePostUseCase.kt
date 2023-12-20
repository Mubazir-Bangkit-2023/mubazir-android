package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class DeletePostUseCase(
    private val foodPostRepository: FoodPostRepository,
    private val userRepository: UserRepository
){
  suspend operator fun invoke(id: String): Result<Unit> = runCatching {
      val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
      foodPostRepository.deletePost("Bearer ${user.token}", id)
  }
}