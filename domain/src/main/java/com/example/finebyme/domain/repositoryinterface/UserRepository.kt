package com.example.finebyme.domain.repositoryinterface

import com.example.finebyme.domain.entity.Photo

interface UserRepository {

    suspend fun getFavoritePhotoList(): List<Photo>

    suspend fun setFavoritePhoto(isAdd: Boolean, photo: Photo)

    //상세화면에 들어갔을때 저장된 포토 좋아요 버튼 처리
    fun isFavoritePhoto(photoId: String): Int
}