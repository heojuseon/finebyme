package com.example.finebyme.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    //singleton
    companion object{
        private var Instance: PhotoDatabase? = null

        fun getInstance(context: Context): PhotoDatabase?{
            if (Instance == null){
                synchronized(PhotoDatabase::class){    //스레드 간의 동기화, 여러 스레드 동시에 접근 못함
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhotoDatabase::class.java,
                        "photo.db"
                    ).build()
                }
            }
            return Instance
        }
    }
}