package com.example.finebyme.data.remote.model

import com.google.gson.annotations.SerializedName

data class PhotoData(
    @SerializedName("id")
    val id: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("color")
    val color: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("alt_description")
    val altDescription: String,

    @SerializedName("user")
    val user: User,

    @SerializedName("urls")
    val urls: Urls,

    @SerializedName("likes")
    val likes: Int


) {
    override fun toString(): String {
        return super.toString()
    }
}

data class Urls(
    @SerializedName("raw")
    val raw: String,

    @SerializedName("full")
    val full: String,

    @SerializedName("regular")
    val regular: String,

    @SerializedName("small")
    val small: String,

    @SerializedName("thumb")
    val thumb: String,

    @SerializedName("small_s3")
    val small_s3: String
)

data class User(
    @SerializedName("username")
    val username: String,

    @SerializedName("name")
    val name: String
)

