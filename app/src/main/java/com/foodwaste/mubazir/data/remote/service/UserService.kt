package com.foodwaste.mubazir.data.remote.service

import com.foodwaste.mubazir.data.remote.payload.Api
import com.foodwaste.mubazir.data.remote.payload.SignInRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpRequest
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.data.remote.payload.UserPostResponse
import com.foodwaste.mubazir.data.remote.payload.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("auth/register")
    suspend fun signUp(
        @Body
        req: SignUpRequest
    ): Response<MessageResponse>

    @POST("auth/login")
    suspend fun signIn(
        @Body
        req: SignInRequest
    ): Response<Api<UserResponse>>

    //Get user post
    @GET("/user/posts")
    suspend fun getUserPosts(
        @Header("Authorization") token: String,
    ) : Response<UserPostResponse>
}