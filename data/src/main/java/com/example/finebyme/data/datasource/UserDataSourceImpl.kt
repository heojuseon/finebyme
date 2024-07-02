package com.example.finebyme.data.datasource

import com.example.finebyme.data.datasource.db.FavoritePhotoDAO
import com.example.finebyme.data.dto.FavoritePhoto
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val favoritePhotoDAO: FavoritePhotoDAO
): UserDataSource {

    override suspend fun insertPhoto(favoritePhoto: FavoritePhoto) {
        return favoritePhotoDAO.insertPhoto(favoritePhoto)
    }

//    override suspend fun getFavoritePhotoList(): List<Photo> {
//        return favoritePhotoDAO.getFavoritePhotoList()
//    }
    override suspend fun getFavoritePhotoList(): List<FavoritePhoto> {
        return favoritePhotoDAO.getFavoritePhotoList()
    }

    override fun isFavoritePhoto(photoId: String): Int {
        return favoritePhotoDAO.isFavorite(photoId)
    }

    override suspend fun deletePhoto(photoId: String) {
        return favoritePhotoDAO.deletePhoto(photoId)
    }

}