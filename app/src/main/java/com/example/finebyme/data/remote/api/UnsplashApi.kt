package com.example.finebyme.data.remote.api

import com.example.finebyme.BuildConfig
import com.example.finebyme.data.remote.model.PhotoData
import retrofit2.Response
import retrofit2.http.GET


interface UnsplashApi {
    @GET(
        "photos/random?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
                "&count=5"
    )
    suspend fun getPhotoList(): Response<List<PhotoData>>
//    suspend fun getPhotoList(): List<PhotoData>
}