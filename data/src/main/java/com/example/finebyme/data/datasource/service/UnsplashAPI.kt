package com.example.finebyme.data.datasource.service

import com.example.finebyme.data.BuildConfig
import com.example.finebyme.data.dto.UnsplashPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPI {
    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
                "&count=30"
    )
    suspend fun getPhotoList(): Response<List<UnsplashPhoto>> //전체 random 리스트만 출력

    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
                "&count=30"
    )
    suspend fun getSearchPhoto(
        @Query("query") query: String  //검색입력 데이터 출력
    ): Response<List<UnsplashPhoto>>
//    suspend fun getPhotoList(): List<PhotoData>
}