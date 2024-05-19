package com.example.finebyme.data.remote.repository

import com.example.finebyme.data.remote.api.UnsplashApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

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