package com.example.roadtofit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roadtofit.data.model.User
import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserPreference) : ViewModel() {

    val toastText: LiveData<Event<String>> = repo.toastText

    fun getSession(): LiveData<User> {
        return repo.getSession()
    }



}