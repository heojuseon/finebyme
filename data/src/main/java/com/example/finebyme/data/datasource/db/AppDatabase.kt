package com.example.finebyme.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finebyme.data.dto.FavoritePhoto
@Database(
    entities = [FavoritePhoto::class],
    version = 1,
    exportSchema = false    // exportSchema :  Room의 Schema 구조를 폴더로 Export 할 수 있다.(true)
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritePhotoDao(): FavoritePhotoDAO
}