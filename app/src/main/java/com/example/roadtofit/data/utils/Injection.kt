package com.example.roadtofit.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.retrofit.ApiConfig
import com.example.roadtofit.data.retrofit.ApiConfig2

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): UserPreference {
        val preferences = SessionPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val apiService2 = ApiConfig2.getApiService()
        return UserPreference.getInstance(preferences, apiService, apiService2)
    }
}