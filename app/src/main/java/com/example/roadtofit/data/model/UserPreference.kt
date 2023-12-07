package com.example.roadtofit.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.roadtofit.data.response.*
import com.example.roadtofit.data.retrofit.ApiService
import com.example.roadtofit.data.retrofit.ApiService2
import com.example.roadtofit.data.utils.Event
import com.example.roadtofit.data.utils.SessionPreferences
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPreference private constructor(
    private val apiService: ApiService,
    private val apiService2: ApiService2,
    private val pref: SessionPreferences
){
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse


    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun postRegister(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = apiService.doRegister(RegisterRequest(name, email, password) )

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _registerResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        val client = apiService.doLogin(email, password)

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun postProfile(id: String, email: String, username: String) {
        _isLoading.value = true
        val client = apiService.doUpdate(ProfileRequest(id, email, username))

        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _profileResponse.value = response.body()
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.errorBody()?.string().toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

//    fun postHistory(category: String, user_id: String) {
//        _isLoading.value = true
//        val client = apiService.doHistory(category, user_id)
//
//        client.enqueue(object : Callback<HistoryResponse> {
//            override fun onResponse(
//                call: Call<HistoryResponse>,
//                response: Response<HistoryResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful && response.body() != null) {
//                    _historyResponse.value = response.body()
//                } else {
//                    _toastText.value = Event(response.message().toString())
//                    Log.e(
//                        TAG,
//                        "onFailure: ${response.message()}, ${response.errorBody()?.string().toString()}"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
//                _toastText.value = Event(t.message.toString())
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }


    fun getId(): Flow<String> {
        return pref.getId()
    }

    suspend fun saveId(id: String) {
        pref.saveId(id)
    }

    fun getSession(): LiveData<User> {
        return pref.getSession().asLiveData()
    }

    suspend fun saveSession(session: User) {
        pref.saveSession(session)
    }

    suspend fun login() {
        pref.login()
    }

    suspend fun logout() {
        pref.logout()
    }

    companion object {
        private const val TAG = "UserPreference"

        @Volatile
        private var instance: UserPreference? = null
        fun getInstance(
            preferences: SessionPreferences,
            apiService: ApiService,
            apiService2: ApiService2
        ): UserPreference =
            instance ?: synchronized(this) {
                instance ?: UserPreference(apiService,apiService2, preferences)
            }.also { instance = it }
    }
}