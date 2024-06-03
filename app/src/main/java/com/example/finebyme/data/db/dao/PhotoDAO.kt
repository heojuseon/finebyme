package com.example.finebyme.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finebyme.data.db.entity.Photo

@Dao
interface PhotoDAO {    //데이터베이스에 접근하여 수행할 작업을 메서드 형태로 정의

    @Insert(onConflict = OnConflictStrategy.REPLACE)    //이미 저장된 항목 있는 경우 데이터 덮어씀
    suspend fun insert(photo: Photo)

    @Query("select * from photos order by id desc")
    fun getAll(): List<Photo>

    // count 가 1일 경우 0일 경우 true / false
    @Query("select count(*) from photos where id = :photoId")
    fun isFavorite(photoId: String): Int

    @Query("delete from photos where id = :photoId")
    suspend fun deletePhoto(photoId: String)
}