package com.foodwaste.mubazir.data.local.datastore

import android.location.Location
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.foodwaste.mubazir.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    suspend fun saveUser(user: UserEntity) {
        dataStore.edit { preferences ->
            preferences[KeyUserId] = user.id
            preferences[KeyUserEmail] = user.email
            preferences[KeyUserName] = user.name
            preferences[KeyUserNoHp] = user.noHp
            if (user.photo != null) preferences[KeyUserPhoto] = user.photo
            preferences[KeyUserToken] = user.token
        }
    }

    fun getUser(): Flow<UserEntity?> {
        return dataStore.data.map { preferences ->
            UserEntity(
                id = preferences[KeyUserId] ?: return@map null,
                name = preferences[KeyUserName] ?: return@map null,
                email = preferences[KeyUserEmail] ?: return@map null,
                noHp = preferences[KeyUserNoHp] ?: return@map null,
                photo = preferences[KeyUserPhoto],
                token = preferences[KeyUserToken] ?: return@map null
            )
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { mutablePreferences ->
            mutablePreferences.remove(KeyUserId)
            mutablePreferences.remove(KeyUserName)
            mutablePreferences.remove(KeyUserEmail)
            mutablePreferences.remove(KeyUserNoHp)
            mutablePreferences.remove(KeyUserPhoto)
            mutablePreferences.remove(KeyUserToken)
        }
    }

    suspend fun setStoredLocation(location: Location) {
        dataStore.edit { preferences ->
            preferences[KeyLatitude] = location.latitude.toString()
            preferences[KeyLongitude] = location.longitude.toString()
        }
    }

    fun getStoredLocation(): Flow<Location?> {
        return dataStore.data.map { preferences ->
            Location("").apply {
                latitude = preferences[KeyLatitude]?.toDouble() ?: return@map null
                longitude = preferences[KeyLongitude]?.toDouble() ?: return@map null
            }
        }
    }

    suspend fun setDarkTheme(isDarkTheme: Boolean?) {
        dataStore.edit { mutablePreferences ->
            if (isDarkTheme != null) {
                mutablePreferences[KeyDarkTheme] = isDarkTheme
                return@edit
            }
            mutablePreferences.remove(KeyDarkTheme)
        }
    }

    fun getDarkTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KeyDarkTheme] ?: false
        }
    }

    companion object {
        private val KeyUserId = stringPreferencesKey("user_id")
        private val KeyUserEmail = stringPreferencesKey("user_email")
        private val KeyUserName = stringPreferencesKey("user_name")
        private val KeyUserPhoto = stringPreferencesKey("user_photo")
        private val KeyUserNoHp = stringPreferencesKey("user_no_hp")
        private val KeyUserToken = stringPreferencesKey("user_token")
        private val KeyLatitude = stringPreferencesKey("latitude")
        private val KeyLongitude = stringPreferencesKey("longitude")
        private val KeyDarkTheme = booleanPreferencesKey("dark_theme")
    }
}