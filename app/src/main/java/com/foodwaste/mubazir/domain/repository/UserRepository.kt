package com.foodwaste.mubazir.domain.repository

import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signUp(name: String, email: String, noHp: String, password: String): MessageResponse
    suspend fun signIn(email: String, password: String): User
    fun getUser() : Flow<User?>
}