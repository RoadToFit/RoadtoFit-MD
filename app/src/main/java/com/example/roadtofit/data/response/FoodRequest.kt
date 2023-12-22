package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class FoodRequest(
    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("activity")
    val activity: String,

    @field:SerializedName("plan")
    val plan: String,

    @field:SerializedName("num_meals")
    val numMeals: Int,

    @field:SerializedName("bodyType")
    val bodyType: String
)
