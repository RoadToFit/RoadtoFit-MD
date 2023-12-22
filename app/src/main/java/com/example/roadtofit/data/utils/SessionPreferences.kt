package com.example.roadtofit.data.utils

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.roadtofit.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>){

    fun getSession(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USER_ID_KEY] ?:"",
                preferences[NAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[GENDER_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveSession(session: User) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = session.userId
            preferences[NAME_KEY] = session.name
            preferences[TOKEN_KEY] = session.token
            preferences[GENDER_KEY] = session.gender
            preferences[STATE_KEY] = session.isLogin

            Log.d(TAG, "Saved token: ${session}")
        }
    }

    suspend fun saveId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    fun getId(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY] ?: ""
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getAccessToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            val accessToken = preferences[TOKEN_KEY] ?: ""
            // Log the retrieved token
            Log.d(TAG, "Retrieved token: $accessToken")
            accessToken
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null
        private val USER_ID_KEY = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val GENDER_KEY = stringPreferencesKey("gender")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}