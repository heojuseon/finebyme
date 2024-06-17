package com.example.finebyme.data.db.repository

import com.example.finebyme.data.db.dao.PhotoDAO
import com.example.finebyme.data.db.entity.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRoomRepository @Inject constructor(private val photoDAO: PhotoDAO) {

    suspend fun insertPhoto(photo: Photo){
        withContext(Dispatchers.IO) {
            photoDAO.insert(photo)
        }
    }

    fun getAllPhoto(): List<Photo>{
        return photoDAO.getAll()
    }

    fun isFavorite(photoId: String): Int{
        return photoDAO.isFavorite(photoId)
    }

    suspend fun deletePhoto(potoId: String){
        withContext(Dispatchers.IO){
            photoDAO.deletePhoto(potoId)
        }
    }
}