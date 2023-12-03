package com.foodwaste.mubazir.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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

    companion object {
        private val KeyUserId = stringPreferencesKey("user_id")
        private val KeyUserEmail = stringPreferencesKey("user_email")
        private val KeyUserName = stringPreferencesKey("user_name")
        private val KeyUserPhoto = stringPreferencesKey("user_photo")
        private val KeyUserNoHp = stringPreferencesKey("user_no_hp")
        private val KeyUserToken = stringPreferencesKey("user_token")
    }
}