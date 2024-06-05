package com.example.finebyme.data.remote.api

import com.example.finebyme.BuildConfig
import com.example.finebyme.data.remote.model.PhotoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UnsplashApi {
    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
                "&count=30"
    )
    suspend fun getPhotoList(): Response<List<PhotoData>> //전체 random 리스트만 출력

    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
                "&count=30"
    )
    suspend fun getSearchPhoto(
        @Query("query") query: String  //검색입력 데이터 출력
    ): Response<List<PhotoData>>
//    suspend fun getPhotoList(): List<PhotoData>
}