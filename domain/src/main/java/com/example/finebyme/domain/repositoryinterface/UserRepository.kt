package com.example.finebyme.domain.repositoryinterface

import com.example.finebyme.domain.entity.Photo

interface UserRepository {

    suspend fun getFavoritePhotoList(): List<Photo>

    suspend fun setFavoritePhoto(isAdd: Boolean, photo: Photo): List<Photo>
}