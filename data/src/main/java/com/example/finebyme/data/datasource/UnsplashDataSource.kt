package com.example.finebyme.data.datasource

import com.example.finebyme.data.dto.UnsplashPhoto
import retrofit2.Response

interface UnsplashDataSource {

    //fake
//    suspend fun getPhotoListFake(): List<UnsplashPhoto>

    //전체 랜덤 리스트 조회
    suspend fun getPhotoList(): Response<List<UnsplashPhoto>>

    //이미지 검색
    suspend fun getSearchPhoto(query: String): Response<List<UnsplashPhoto>>

}