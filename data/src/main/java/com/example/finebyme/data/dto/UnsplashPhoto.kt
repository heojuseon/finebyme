package com.example.finebyme.data.dto

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("width")
    val width: Int = 0,

    @SerializedName("height")
    val height: Int = 0,

    @SerializedName("description")
    val description: String?,

    @SerializedName("alt_description")
    val altDescription: String?,

    @SerializedName("urls")
    val urls: UnsplashPhotoUrls
)
