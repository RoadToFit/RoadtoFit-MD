package com.example.roadtofit.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.RegisterResponse
import com.example.roadtofit.data.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: UserPreference) : ViewModel() {

    val registerResponse: LiveData<RegisterResponse> = repo.registerResponse
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun doRegister(username: String, password: String,name: String,gender: String) {
        viewModelScope.launch {
            repo.postRegister( username, password, name, gender)
        }
    }
}