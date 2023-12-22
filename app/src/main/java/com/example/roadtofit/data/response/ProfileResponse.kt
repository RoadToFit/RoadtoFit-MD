package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: Users
)


data class Users(
    @field:SerializedName("userId")
    val userId: Int,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("foodRecommendations")
    val foodRecommendations: List<FoodRecommendation>,

    @field:SerializedName("activityRecommendations")
    val activityRecommendations: List<ActivityRecommendation>,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class FoodRecommendation(
    @field:SerializedName("foodId")
    val foodId: String,

    @field:SerializedName("menu")
    val menu: String,

    @field:SerializedName("calories")
    val calories: Int,

    @field:SerializedName("proteins")
    val proteins: Int,

    @field:SerializedName("fat")
    val fat: Int,

    @field:SerializedName("carbohydrate")
    val carbo: Int,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class ActivityRecommendation(
    @field:SerializedName("activityId")
    val activityId: String,

    @field:SerializedName("activity")
    val activity: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("calPerHour")
    val calPerHour: Int,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)


