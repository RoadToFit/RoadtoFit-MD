package com.example.roadtofit.data.retrofit


import com.example.roadtofit.data.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @POST("/users/login")
    fun doLogin(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/users/register")
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>

    @PUT("/users")
    fun doUpdate(
        @Header("Authorization") token: String,
        @Body profileRequest: ProfileRequest
    ): Call<LoginResponse>

    @Multipart
    @PUT("/users/image")
    fun uploadProfile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Call<LoginResponse>

    @GET("/users/list")
    fun getUser(
        @Header("Authorization") token: String
    ): Call<ProfileResponse>




}