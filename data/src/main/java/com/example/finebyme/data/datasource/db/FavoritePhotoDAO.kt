package com.example.finebyme.data.datasource.db

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finebyme.data.dto.FavoritePhoto

@Dao
interface FavoritePhotoDAO {    //데이터베이스에 접근하여 수행할 작업을 메서드 형태로 정의
    @Insert(onConflict = OnConflictStrategy.REPLACE)    //이미 저장된 항목 있는 경우 데이터 덮어씀
    suspend fun insertPhoto(favoritePhoto: FavoritePhoto)

    @Query("select * from photos order by autoId desc")
    fun getFavoritePhotoList(): List<FavoritePhoto>

    // count 가 1일 경우 0일 경우 true / false
    @Query("select count(*) from photos where id = :photoId")
    fun isFavorite(photoId: String): Int

    @Query("delete from photos where id = :photoId")
    suspend fun deletePhoto(photoId: String)

    @Query("SELECT * FROM photos")
    fun getAllCursor(): Cursor

}