package com.foodwaste.mubazir.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.foodwaste.mubazir.data.local.UserLocalDataSource
import com.foodwaste.mubazir.data.local.datastore.UserPreferences
import com.foodwaste.mubazir.data.local.room.FoodPostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    private val Context.dataStore by preferencesDataStore(name = "app_preferences")

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext
        context: Context
    ): UserPreferences {
        return UserPreferences(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userPreferences: UserPreferences): UserLocalDataSource {
        return UserLocalDataSource(userPreferences)
    }

    @Provides
    @Singleton
    fun providePostDatabase(@ApplicationContext context: Context): FoodPostDatabase {
        return Room.databaseBuilder(
            context,
            FoodPostDatabase::class.java,
            "post_db"
        ).build()
    }
}