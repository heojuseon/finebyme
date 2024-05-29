package com.example.finebyme.data.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.finebyme.data.db.dao.PhotoDAO
import com.example.finebyme.data.db.database.PhotoDatabase
import com.example.finebyme.data.db.entity.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRoomRepository(application: Application) {
    private val photoDAO: PhotoDAO
    private val photoList: LiveData<List<Photo>>
    //초기화
    init {
        val db = PhotoDatabase.getInstance(application)!!
        photoDAO = db.photoDao()
        photoList = db.photoDao().getAll()
    }

    suspend fun insertPhoto(photo: Photo){
        withContext(Dispatchers.IO) {
            photoDAO.insert(photo)
        }
    }

    fun getAllPhoto(): LiveData<List<Photo>>{
        return photoDAO.getAll()
    }
}