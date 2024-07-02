package com.example.finebyme.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "photos", indices = [Index(value = ["id"], unique = true)])   //데이터 베이스의 테이블 행 역할
data class FavoritePhoto(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 0,

    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "width")
    val width: Int = 0,

    @ColumnInfo(name = "height")
    val height: Int = 0,

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "altDescription")
    val altDescription: String = "",

    @ColumnInfo(name = "thumbUrl")
    val thumbUrl: String = "",

    @ColumnInfo(name = "fullUrl")
    val fullUrl: String = ""
)
