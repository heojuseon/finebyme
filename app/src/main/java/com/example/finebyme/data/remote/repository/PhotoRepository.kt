package com.example.finebyme.data.remote.repository

import com.example.finebyme.data.remote.api.UnsplashApi
import com.example.finebyme.data.remote.model.PhotoData
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/*
싱글톤 클래스 정의
- 한번의 생성으로 프로젝트 내의 모든 클래스에서 접근 가능
 */
//object PhotoRepository {
//
//    private const val BASE_URL = "https://api.unsplash.com/"
//
//    val unsplashApi: UnsplashApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create()
//    }
//}

@Singleton
class PhotoRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    suspend fun getPhotoList(): Response<List<PhotoData>> {
        return unsplashApi.getPhotoList()
    }


}