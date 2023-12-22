package com.example.roadtofit.data.retrofit

import com.example.roadtofit.data.response.BodyResponse
import com.example.roadtofit.data.response.DietRequest
import com.example.roadtofit.data.response.DietResponse
import com.example.roadtofit.data.response.FoodRequest
import com.example.roadtofit.data.response.FoodResponse
import com.example.roadtofit.data.response.LoginRequest
import com.example.roadtofit.data.response.LoginResponse
import com.example.roadtofit.data.response.ProfileRequest
import com.example.roadtofit.data.response.ProfileResponse
import com.example.roadtofit.data.response.RegisterRequest
import com.example.roadtofit.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ApiService2 {
    @Multipart
    @POST("/model/body-classifier")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<BodyResponse>

    @POST("/model/activities-recommendation")
    fun doActivities(
        @Body dietRequest: DietRequest
    ): Call<DietResponse>

    @POST("/model/food-recommendation")
    fun doFood(
        @Body foodRequest: FoodRequest
    ): Call<FoodResponse>
}