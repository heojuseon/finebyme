package com.example.finebyme.data.datasource

import com.example.finebyme.data.dto.FavoritePhoto

interface UserDataSource {

    suspend fun insertPhoto(favoritePhoto: FavoritePhoto)

    suspend fun getFavoritePhotoList(): List<FavoritePhoto>

    fun isFavoritePhoto(photoId: String): Int

    suspend fun deletePhoto(photoId: String)
}