package com.example.finebyme.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "photos")   //데이터 베이스의 테이블 행 역할
data class PhotoData(
    @SerializedName("id")
    @PrimaryKey
    val id: String,

    @SerializedName("width")
    @ColumnInfo(name = "width")
    val width: Int,

    @SerializedName("height")
    @ColumnInfo(name = "height")
    val height: Int,

    //json 데이터에 null 값이 종종 있음
    @SerializedName("description")
    @ColumnInfo(name = "description")
    val description: String?,

    //json 데이터에 null 값이 종종 있음
    @SerializedName("alt_description")
    @ColumnInfo(name = "altDescription")
    val altDescription: String?,


    @SerializedName("urls")
    @ColumnInfo(name = "url")
    val urls: Urls
): Parcelable

@Parcelize
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
): Parcelable
