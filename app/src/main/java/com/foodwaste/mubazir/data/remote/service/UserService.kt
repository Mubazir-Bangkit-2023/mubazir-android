package com.foodwaste.mubazir.data.remote.service

import com.foodwaste.mubazir.data.remote.payload.Api
import com.foodwaste.mubazir.data.remote.payload.SignInRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpResponse
import com.foodwaste.mubazir.data.remote.payload.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("auth/register")
    suspend fun signUp(
        @Body
        req: SignUpRequest
    ): Response<SignUpResponse>

    @POST("auth/login")
    suspend fun signIn(
        @Body
        req: SignInRequest
    ): Response<Api<UserResponse>>
}