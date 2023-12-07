package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val data: Data? = null
)

data class Data(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("username")
    val name: String,

    @field:SerializedName("email")
    val email: String
)