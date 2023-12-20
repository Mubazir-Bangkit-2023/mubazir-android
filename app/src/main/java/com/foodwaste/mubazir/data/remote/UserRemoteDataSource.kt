package com.foodwaste.mubazir.data.remote

import com.foodwaste.mubazir.data.remote.payload.FoodPostDetailResponse
import com.foodwaste.mubazir.data.remote.payload.SignInRequest
import com.foodwaste.mubazir.data.remote.payload.SignUpRequest
import com.foodwaste.mubazir.data.remote.payload.MessageResponse
import com.foodwaste.mubazir.data.remote.payload.UserResponse
import com.foodwaste.mubazir.data.remote.service.UserService
import okhttp3.ResponseBody
import org.json.JSONObject

class UserRemoteDataSource(
    private val userService: UserService
) {
    suspend fun signUp(req: SignUpRequest): MessageResponse {
        val res = userService.signUp(req)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let {
                throw RuntimeException(res.body()?.message)
            }
        }
        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun signIn(req: SignInRequest): UserResponse {
        val res = userService.signIn(req)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }
        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun getUserPosts(token: String) : List<FoodPostDetailResponse> {
        val res = userService.getUserPosts(token)
        val data = res.takeIf { it.isSuccessful }?.body()?.posts
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }
        return data ?: throw RuntimeException("Response body is empty")
    }
}

fun ResponseBody.getErrorMessage(key: String = "data"): String {
    return JSONObject(charStream().readText()).getString(key)
}