package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class DietRequest(

    @field:SerializedName("input")
    val input: String
)