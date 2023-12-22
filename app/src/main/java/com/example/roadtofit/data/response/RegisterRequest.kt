package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("gender")
	val gender: String
)
