    package com.example.roadtofit.data.response

    import com.google.gson.annotations.SerializedName

    data class LoginResponse(
        @field:SerializedName("message")
        val message: String,

        @field:SerializedName("success")
        val success: Boolean,

        @field:SerializedName("user")
        val user: User? = null,

        @SerializedName("token")
        val token: String
    )

    data class User(
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

        @field:SerializedName("weight")
        val weight: Double? = null,

        @field:SerializedName("height")
        val height: Double? = null,

        @field:SerializedName("imageUrl")
        val imageUrl: String? = null,

        @field:SerializedName("createdAt")
        val createdAt: String,

        @field:SerializedName("updatedAt")
        val updatedAt: String,
    )


