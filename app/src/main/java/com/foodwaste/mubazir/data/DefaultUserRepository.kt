package com.foodwaste.mubazir.data

import android.location.Location
import com.foodwaste.mubazir.data.local.UserLocalDataSource
import com.foodwaste.mubazir.data.mapper.toEntity
import com.foodwaste.mubazir.data.mapper.toModel
import com.foodwaste.mubazir.data.remote.UserRemoteDataSource
import com.foodwaste.mubazir.data.remote.payload.SignInRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpRequest
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.domain.model.FoodPostDetail
import com.foodwaste.mubazir.domain.model.User
import com.foodwaste.mubazir.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun signUp(
        name: String,
        email: String,
        noHp: String,
        password: String
    ): MessageResponse {
        val req = SignUpRequest(
            name = name,
            email = email,
            noHp = noHp,
            password = password
        )
        return userRemoteDataSource.signUp(req)
    }

    override suspend fun signIn(email: String, password: String): User {
        val req = SignInRequest(
            email = email,
            password = password
        )
        val res = userRemoteDataSource.signIn(req)
        userLocalDataSource.save(res.toEntity())
        return res.toModel()
    }

    override suspend fun getUserPost(token: String): List<FoodPostDetail> {
        val res = userRemoteDataSource.getUserPosts(token)
        val posts = res.map {
            it.toModel()
        }
        return posts
    }

    override fun getUser(): Flow<User?> {
        return userLocalDataSource.getUser().map { it?.toModel() }
    }

    override suspend fun signOut() {
        userLocalDataSource.delete()
    }

    override suspend fun setStoredLocation(location: Location) {
        userLocalDataSource.setStoredLocation(location)
    }

    override suspend fun getStoredLocation(): Flow<Location?> {
        return userLocalDataSource.getStoredLocation()
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean?) {
        userLocalDataSource.setDarkTheme(isDarkTheme)
    }

    override fun getDarkTheme(): Flow<Boolean?> {
        return userLocalDataSource.getDarkTheme()
    }
}