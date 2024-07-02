package com.example.finebyme.data.repository

import com.example.finebyme.data.datasource.UserDataSource
import com.example.finebyme.data.dto.mapper.PhotoMapper.toFavoritePhoto
import com.example.finebyme.data.dto.mapper.PhotoMapper.toPhoto
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.repositoryinterface.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {

    override suspend fun getFavoritePhotoList(): List<Photo> {
        return userDataSource.getFavoritePhotoList().map { it.toPhoto() }
    }

    override suspend fun setFavoritePhoto(isAdd: Boolean, photo: Photo) {

        if(isAdd){
            userDataSource.insertPhoto(photo.toFavoritePhoto())
        } else{
            //delete
            userDataSource.deletePhoto(photo.id)
        }
    }

    override fun isFavoritePhoto(photoId: String): Int {
        return userDataSource.isFavoritePhoto(photoId)
    }

}
