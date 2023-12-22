package com.example.roadtofit.data.response

import com.google.gson.annotations.SerializedName

data class ProfileRequest(

	@field:SerializedName("name")
	val name: String? = null
)
