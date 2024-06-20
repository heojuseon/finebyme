package com.example.finebyme.data.datasource

import com.example.finebyme.data.dto.UnsplashPhoto

interface UnsplashDataSource {

    suspend fun getPhotoList(): List<UnsplashPhoto>

}