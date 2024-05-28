package com.example.finebyme.data.db.repository

import android.app.Application
import com.example.finebyme.data.db.dao.PhotoDAO
import com.example.finebyme.data.db.database.PhotoDatabase
import com.example.finebyme.data.db.entity.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRoomRepository(application: Application) {
    private val photoDAO: PhotoDAO
    //초기화
    init {
        val db = PhotoDatabase.getInstance(application)!!
        photoDAO = db.photoDao()
    }

    suspend fun insertPhoto(photo: Photo){
        withContext(Dispatchers.IO) {
            photoDAO.insert(photo)
        }
    }
}