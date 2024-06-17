package com.example.finebyme.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finebyme.data.db.dao.PhotoDAO
import com.example.finebyme.data.db.entity.Photo

@Database(
    entities = [Photo::class],
    version = 1,
    exportSchema = false    // exportSchema :  Room의 Schema 구조를 폴더로 Export 할 수 있다.(true)
)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDAO
}