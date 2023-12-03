package com.foodwaste.mubazir.domain.repository

import com.foodwaste.mubazir.data.remote.payload.SignUpResponse
import com.foodwaste.mubazir.domain.model.User

interface UserRepository {

    suspend fun signUp(name: String, email: String, noHp: String, password: String): SignUpResponse
    suspend fun signIn(email: String, password: String): User
}