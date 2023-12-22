package com.example.roadtofit.ui.auth

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.example.roadtofit.data.model.User
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.LoginResponse
import com.example.roadtofit.data.utils.Event
import kotlinx.coroutines.launch


class LoginViewModel(private val repo: UserPreference) : ViewModel() {

    val loginResponse: LiveData<LoginResponse> = repo.loginResponse
    val isLoading: LiveData<Boolean> = repo.isLoading
    val toastText: LiveData<Event<String>> = repo.toastText

    fun doLogin(username: String, password: String) {
        viewModelScope.launch {
            repo.postLogin(username, password)
        }
    }

    fun saveSession(session: User) {
        viewModelScope.launch {
            repo.saveSession(session)

            Log.d(ContentValues.TAG, "Saved token: ${session.name}")
        }
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            repo.saveSession(User(user.userId,user.name, user.token,user.gender, user.isLogin))
        }
    }
    fun getUser(): LiveData<User> {
        return repo.getSession()
    }

    fun login() {
        viewModelScope.launch {
            repo.login()
        }
    }

    fun saveId(userId: String) {
        viewModelScope.launch {
            repo.saveId(userId)
        }
    }
}