package com.example.roadtofit.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.ProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(private val repo: UserPreference) : ViewModel(){

    val profileResponse: LiveData<ProfileResponse> = repo.profileResponse
    private val userId = runBlocking { repo.getId().first() }

    init {
        Log.d ("ProfileViewModel", userId)
    }

    fun doUpdate(email: String, username: String) {
        viewModelScope.launch {
            repo.postProfile(userId, email, username) //yg hardcore ini coba pake userId
        }
    }
}