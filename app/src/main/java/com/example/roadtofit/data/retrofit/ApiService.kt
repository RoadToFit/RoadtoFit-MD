package com.example.roadtofit.data.retrofit

import com.example.roadtofit.data.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/login")
    fun doLogin(
        @Field("email") email: String,
        @Field("password") password : String
    ): Call<LoginResponse>

    @POST("/auth/register")
    fun doRegister(
        @Body registerRequest: RegisterRequest
    ): Call<RegisterResponse>


    @PUT("/edit-profile")
    fun doUpdate(
        @Body profileRequest: ProfileRequest
    ): Call<ProfileResponse>


}