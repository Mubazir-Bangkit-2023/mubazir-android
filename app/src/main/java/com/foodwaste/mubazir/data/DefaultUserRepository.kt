package com.foodwaste.mubazir.data

import com.foodwaste.mubazir.data.local.UserLocalDataSource
import com.foodwaste.mubazir.data.mapper.toEntity
import com.foodwaste.mubazir.data.mapper.toModel
import com.foodwaste.mubazir.data.remote.UserRemoteDataSource
import com.foodwaste.mubazir.data.remote.payload.SignInRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpResponse
import com.foodwaste.mubazir.domain.model.User
import com.foodwaste.mubazir.domain.repository.UserRepository

class DefaultUserRepository(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {

    override suspend fun signUp(
        name: String,
        email: String,
        noHp: String,
        password: String
    ): SignUpResponse {
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
}