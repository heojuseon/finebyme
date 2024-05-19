package com.example.finebyme.data.remote.api

import com.example.finebyme.BuildConfig
import com.example.finebyme.data.remote.model.PhotoData
import retrofit2.http.GET


interface UnsplashApi {
    //자바의 static 과 유사
//    companion object{
//        const val BASE_URL = "https://api.unsplash.com/"
//        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
//    }

    @GET("photos/random?" +
            "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
            "&count=5")
    suspend fun getPhotoList(): List<PhotoData>
}