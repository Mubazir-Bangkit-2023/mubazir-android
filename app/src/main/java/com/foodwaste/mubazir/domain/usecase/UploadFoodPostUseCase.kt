package com.foodwaste.mubazir.domain.usecase

import com.foodwaste.mubazir.domain.repository.FoodPostRepository
import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import timber.log.Timber

class UploadFoodPostUseCase(
    private val foodPostRepository: FoodPostRepository,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(
        title: String,
        categoryId: String,
        freshness: String?,
        price: String,
        pickupTime: String,
        lat: String,
        lon: String,
        description: String,
        image: MultipartBody.Part
    ): Result<Unit> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        try {
            foodPostRepository.postFood(
                token = "Bearer ${user.token}",
                title = title,
                categoryId = categoryId,
                freshness = freshness,
                price = price,
                pickupTime = pickupTime,
                lat = lat,
                lon = lon,
                description = description,
                image = image
            )
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }

    }
}