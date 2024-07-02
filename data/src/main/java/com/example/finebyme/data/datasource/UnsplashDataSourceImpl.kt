package com.example.finebyme.data.datasource

import com.example.finebyme.data.datasource.service.UnsplashAPI
import com.example.finebyme.data.dto.UnsplashPhoto
import retrofit2.Response
import javax.inject.Inject

class UnsplashDataSourceImpl @Inject constructor(
    private val unsplashAPI: UnsplashAPI
): UnsplashDataSource {
    override suspend fun getPhotoList(): Response<List<UnsplashPhoto>> {
//        return unsplashAPI.getPhotoList().body()!!
        return unsplashAPI.getPhotoList()
    }

    override suspend fun getSearchPhoto(query: String): Response<List<UnsplashPhoto>> {
        return unsplashAPI.getSearchPhoto(query)
    }

}