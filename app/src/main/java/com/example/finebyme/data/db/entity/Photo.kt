package com.example.finebyme.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")   //데이터 베이스의 테이블 행 역할
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "altDescription")
    val altDescription: String?,

    @ColumnInfo(name = "url")
    val url: String
)
