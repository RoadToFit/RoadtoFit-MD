package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class DietResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("result")
    val result: List<Int>

)