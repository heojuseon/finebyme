package com.example.finebyme.domain.repositoryinterface

import com.example.finebyme.domain.entity.Photo

interface UnsplashRepository {

    //fake
//    suspend fun getPhotoListFake(): List<Photo>

    suspend fun getPhotoList(): List<Photo>
    suspend fun getSearchPhotoList(query: String): List<Photo>
}