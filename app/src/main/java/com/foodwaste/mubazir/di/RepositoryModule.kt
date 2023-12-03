package com.foodwaste.mubazir.di

import com.foodwaste.mubazir.data.DefaultUserRepository
import com.foodwaste.mubazir.data.local.UserLocalDataSource
import com.foodwaste.mubazir.data.remote.UserRemoteDataSource
import com.foodwaste.mubazir.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        userLocalDataSource: UserLocalDataSource,
        userRemoteDataSource: UserRemoteDataSource
    ): UserRepository {
        return DefaultUserRepository(userLocalDataSource, userRemoteDataSource)
    }
}