package com.example.finebyme.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.finebyme.data.db.entity.Photo

@Dao
interface PhotoDAO {    //데이터베이스에 접근하여 수행할 작업을 메서드 형태로 정의

    @Insert
    suspend fun insert(photo: Photo)

    @Query("select * from photos order by id desc")
    suspend fun getAll(): List<Photo>
}