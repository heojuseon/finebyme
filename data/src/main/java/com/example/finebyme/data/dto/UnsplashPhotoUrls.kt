package com.example.finebyme.data.dto

import com.google.gson.annotations.SerializedName

data class UnsplashPhotoUrls(

    @SerializedName("thumb")
    val thumbUrl: String = "",

    @SerializedName("full")
    val fullUrl: String = ""
)