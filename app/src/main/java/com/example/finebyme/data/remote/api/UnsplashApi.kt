package com.example.finebyme.data.remote.api

import com.example.finebyme.BuildConfig
import retrofit2.http.GET


interface UnsplashApi {
    //자바의 static 과 유사
    companion object{
        const val BASE_URL = "https://api.unsplash.com/"
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
    }

    @GET("photos/random?" +
            "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
            "&count=30")
    suspend fun getRandomPhotos(
    //suspend function : 빌드가 돌아가는 동안 서버 개발(다른 작업 수행)을 한다. -------> coroutine
    //suspend란 비동기 실행을 위한 중단 지점의 의미(즉, 잠시 중단(suspend)한다는 의미이고, 잠시 중단한다면 언젠가는 다시 시작(resume)된다는 뜻.)

    )
}