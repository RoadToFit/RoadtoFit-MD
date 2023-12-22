package com.example.roadtofit.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.roadtofit.data.response.*
import com.example.roadtofit.data.retrofit.ApiService
import com.example.roadtofit.data.retrofit.ApiService2
import com.example.roadtofit.data.utils.Event
import com.example.roadtofit.data.utils.SessionPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPreference private constructor(
    private val apiService: ApiService,
    private val apiService2: ApiService2,
    private val pref: SessionPreferences
) : ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> = _registerResponse

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _bodyResponse = MutableLiveData<BodyResponse>()
    val bodyResponse: LiveData<BodyResponse> = _bodyResponse

    private val _userResponse = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> = _userResponse

    private val _dietResponse = MutableLiveData<DietResponse>()
    val dietResponse: LiveData<DietResponse> = _dietResponse

    private val _foodResponse = MutableLiveData<FoodResponse>()
    val foodResponse: LiveData<FoodResponse> = _foodResponse

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    fun postRegister(username: String,password: String,name: String, gender: String) {
        _isLoading.value = true
        val client = apiService.doRegister(RegisterRequest(username, password, name, gender))

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

    fun postLogin(username: String, password: String) {
        _isLoading.value = true
        val client = apiService.doLogin(LoginRequest(username, password))

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())

                    val token = response.body()?.token ?: ""
                    Log.d(TAG, "Token from response: $token")

                    val currentUser = _user.value ?: User("","", "", "",false)
                    val updatedUser = currentUser.copy(token = token)
                    _user.value = updatedUser

                    Log.d(TAG, "Token from response: $updatedUser")

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

    fun postProfile(name: String) {
        _isLoading.value = true

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val token = pref.getAccessToken().firstOrNull()

                if (token != null) {
                    // Step 1: Perform the PUT operation
                    val updateClient = apiService.doUpdate("Bearer $token", ProfileRequest(name))

                    updateClient.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                // Step 2: Reload the user data after the PUT operation is successful
                                reloadUserData(token)
                            } else {
                                handlePostProfileFailure(response)
                            }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            handlePostProfileFailure(null)
                        }
                    })
                } else {
                    // Token is null
                    Log.e(TAG, "Token is null")
                    _toastText.value = Event("Error: Token is null")
                }
            } catch (e: Exception) {
                handlePostProfileFailure(null)
            }
        }
    }

    private fun reloadUserData(token: String) {
        val getUserClient = apiService.getUser("Bearer $token")

        getUserClient.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                _isLoading.value = false

                if (response.isSuccessful && response.body() != null) {
                    _profileResponse.value = response.body()
                    _toastText.value = Event(response.body()?.message.toString())

                    _user.value = (response.body()?.user ?: User("","", "", "",false)) as User?
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _toastText.value = Event("Error: ${t.message}")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun handlePostProfileFailure(response: Response<LoginResponse>?) {
        _isLoading.value = false
        _toastText.value = Event(response?.message().toString() ?: "Unknown error")
        Log.e(
            TAG,
            "onFailure: ${response?.message()}, ${response?.body()?.message.toString()}"
        )
    }

    fun postFood(gender: String, height:Int, weight: Int, age: Int, activity: String, plan: String,numMeals: Int, bodyType: String) {
        _isLoading.value = true
        val client = apiService2.doFood(FoodRequest(gender,height,weight, age, activity, plan, numMeals, bodyType))

        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(
                call: Call<FoodResponse>,
                response: Response<FoodResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _foodResponse.value = response.body()
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.errorBody()?.string().toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun postPredict(input: String) {
        _isLoading.value = true
        val client = apiService2.doActivities(DietRequest(input))

        client.enqueue(object : Callback<DietResponse> {
            override fun onResponse(
                call: Call<DietResponse>,
                response: Response<DietResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    _dietResponse.value = response.body()
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.errorBody()?.string().toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<DietResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun uploadImage(image: MultipartBody.Part) {
        _isLoading.value = true
        val client = apiService2.uploadImage(image)

        client.enqueue(object : Callback<BodyResponse> {
            override fun onResponse(call: Call<BodyResponse>, response: Response<BodyResponse>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    response.body().also { _bodyResponse.value = it }
                    _toastText.value = Event(response.body()?.message.toString())
                } else {
                    _toastText.value = Event(response.message().toString())
                    Log.e(
                        TAG,
                        "onFailure: ${response.message()}, ${response.body()?.message.toString()}"
                    )
                }
            }

            override fun onFailure(call: Call<BodyResponse>, t: Throwable) {
                _toastText.value = Event(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun uploadProfile(image: MultipartBody.Part) {
        _isLoading.value = true
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val token = pref.getAccessToken().firstOrNull()

                if (token != null) {
                    Log.d(TAG, "Token is not null: $token")

                    val client = apiService.uploadProfile("Bearer "+token, (image))
                    Log.d(TAG, "Request URL: ${client.request().url}")
                    Log.d(TAG, "Request Headers: ${client.request().headers}")

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
                            _toastText.value = Event("Error: ${t.message}")
                            Log.e(TAG, "onFailure: ${t.message}")
                        }
                    })
                } else {
                    // Token is null
                    Log.e(TAG, "Token is null")

                    _toastText.value = Event("Error: Token is null")
                }
            } catch (e: Exception) {
                _toastText.value = Event("Error: ${e.message}")
                Log.e(TAG, "Exception: ${e.message}")
            }
        }
    }

    fun getId(): Flow<String> {
        return pref.getId()
    }

    suspend fun saveId(userId: String) {
        pref.saveId(userId)
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
            apiService2: ApiService2,
        ): UserPreference =
            instance ?: synchronized(this) {
                instance ?: UserPreference(apiService,apiService2, preferences)
            }.also { instance = it }
    }
}