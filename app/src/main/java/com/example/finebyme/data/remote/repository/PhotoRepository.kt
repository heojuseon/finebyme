package com.example.finebyme.data.remote.repository

import com.example.finebyme.data.remote.api.UnsplashApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/*
싱글톤 클래스 정의
- 한번의 생성으로 프로젝트 내의 모든 클래스에서 접근 가능
 */
object PhotoRepository {

    const val BASE_URL = "https://api.unsplash.com/"

    val unsplashApi: UnsplashApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}