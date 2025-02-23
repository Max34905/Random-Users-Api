package com.example.retrofitandpictureloading.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results") val result: List<User>
)

data class User(
    @SerializedName("gender") val gender: String = "",
    @SerializedName("name") val name: Name,
    @SerializedName("picture") val picture: Picture
)

data class Name(
    @SerializedName("title") val title: String = "",
    @SerializedName("first") val firstName: String = "",
    @SerializedName("last") val lastName: String = ""
)

data class Picture(
    @SerializedName("large") val large: String = "",
    @SerializedName("medium") val medium: String = "",
    @SerializedName("thumbnail") val thumbnail: String = ""
)
