package com.example.roadtofit.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadtofit.data.model.User
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.ProfileResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ProfileViewModel(private val repo: UserPreference) : ViewModel(){

    val profileResponse: LiveData<ProfileResponse> = repo.profileResponse
    val userSession: LiveData<User> = repo.getSession()
    private val userId = runBlocking { repo.getId().first() }

    init {
        Log.d ("ProfileViewModel", userId)
    }

    fun uploadProfile(image: ByteArray) {
        viewModelScope.launch {
            val requestImagePart = image.toRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestImagePart)
            repo.uploadProfile(imagePart)
        }
    }

    fun doUpdate(name: String) {
        viewModelScope.launch {
            repo.postProfile(name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repo.logout()
        }
    }
}