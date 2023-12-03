package com.foodwaste.mubazir.data.local

import com.foodwaste.mubazir.data.local.datastore.UserPreferences
import com.foodwaste.mubazir.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserLocalDataSource(
    private val userPreferences: UserPreferences
) {
    suspend fun save(user: UserEntity) {
        userPreferences.saveUser(user)
    }

    fun getUser(): Flow<UserEntity?> {
        return userPreferences.getUser()
    }
}