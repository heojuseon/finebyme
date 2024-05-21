package com.example.finebyme.data.remote.repository

import com.example.finebyme.data.remote.api.UnsplashApi
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.reflect.Type

/*
싱글톤 클래스 정의
- 한번의 생성으로 프로젝트 내의 모든 클래스에서 접근 가능
 */
object PhotoRepository {

    private const val BASE_URL = "https://api.unsplash.com/"

    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
            Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() == 0L) null else nextResponseBodyConverter.convert(value)
        }
    }

    val unsplashApi: UnsplashApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}