package com.example.finebyme.data.remote.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoData(
    @SerializedName("id")
    val id: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    //json 데이터에 null 값이 종종 있음
    @SerializedName("description")
    val description: String?,

    //json 데이터에 null 값이 종종 있음
    @SerializedName("alt_description")
    val altDescription: String?,


    @SerializedName("urls")
    val urls: Urls
): Parcelable

@Parcelize
data class Urls(
    @SerializedName("raw")
    val raw: String = "",

    @SerializedName("full")
    val full: String = "",

    @SerializedName("regular")
    val regular: String = "",

    @SerializedName("small")
    val small: String = "",

    @SerializedName("thumb")
    val thumb: String ="",

    @SerializedName("small_s3")
    val small_s3: String = ""
): Parcelable
