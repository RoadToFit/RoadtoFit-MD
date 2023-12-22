package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("user")
    val user2: User2? = null
    )
    data class User2 (
        @field:SerializedName("userId")
        val userId: Int,

        @field:SerializedName("username")
        val email: String,

        @field:SerializedName("name")
        val name: String,

        @field:SerializedName("gender")
        val gender: String,

        @field:SerializedName("age")
        val age: Int? = null,

        @field:SerializedName("bodytype")
        val bodyType: String,

        @field:SerializedName("bmi")
        val bmi: String,

        @field:SerializedName("foodRecommendations")
        val foodRecommendations: List<FoodRecommendation>,

        @field:SerializedName("activityRecommendations")
        val activityRecommendations: List<ActivityRecommendation>,

        @field:SerializedName("imageUrl")
        val imageUrl: String? = null,

        @field:SerializedName("createdAt")
        val createdAt: String,

        @field:SerializedName("updatedAt")
        val update: String
    )
