package com.foodwaste.mubazir.di

import com.foodwaste.mubazir.BuildConfig
import com.foodwaste.mubazir.data.local.room.FoodPostDatabase
import com.foodwaste.mubazir.data.remote.FoodClassificationRemoteDataSource
import com.foodwaste.mubazir.data.remote.FoodPostRemoteDataSource
import com.foodwaste.mubazir.data.remote.UserRemoteDataSource
import com.foodwaste.mubazir.data.remote.service.FoodClassificationService
import com.foodwaste.mubazir.data.remote.service.FoodPostService
import com.foodwaste.mubazir.data.remote.service.UserService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }
        val callFactory = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodClassificationRemoteDataSource(): FoodClassificationRemoteDataSource {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY
        }
        val callFactory = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .setLenient()
            .create()

        val foodClassificationService = Retrofit.Builder()
            .baseUrl(BuildConfig.ML_MODEL_BASE_URL)
            .callFactory(callFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create<FoodClassificationService>()
        return FoodClassificationRemoteDataSource(foodClassificationService)
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(retrofit: Retrofit): UserRemoteDataSource {
        val userService = retrofit.create<UserService>()
        return UserRemoteDataSource(userService)
    }

    @Provides
    @Singleton
    fun provideFoodPostRemoteDataSource(retrofit: Retrofit, foodPostDatabase: FoodPostDatabase): FoodPostRemoteDataSource {
        val foodPostService = retrofit.create<FoodPostService>()
        return FoodPostRemoteDataSource(foodPostService, foodPostDatabase)
    }

}