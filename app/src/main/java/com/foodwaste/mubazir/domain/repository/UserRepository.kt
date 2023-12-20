package com.foodwaste.mubazir.domain.repository

import android.location.Location
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signUp(name: String, email: String, noHp: String, password: String): MessageResponse
    suspend fun signIn(email: String, password: String): User

    suspend fun getUserPost(token: String): List<FoodPostDetail>
    fun getUser(): Flow<User?>
    suspend fun signOut()
    suspend fun setStoredLocation(location: Location)
    suspend fun getStoredLocation(): Flow<Location?>
    suspend fun setDarkTheme(isDarkTheme: Boolean?)
    fun getDarkTheme(): Flow<Boolean?>
}