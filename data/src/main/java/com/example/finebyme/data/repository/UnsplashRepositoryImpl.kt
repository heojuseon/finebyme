package com.example.finebyme.data.repository

import android.util.Log
import com.example.finebyme.data.datasource.UnsplashDataSource
import com.example.finebyme.data.dto.mapper.PhotoMapper.toDomain
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashDataSource: UnsplashDataSource
) : UnsplashRepository {
    override suspend fun getPhotoList(): List<Photo>{
        return try {
            // 네트워크 요청을 IO 스레드에서 실행
            val response = withContext(Dispatchers.IO){
                unsplashDataSource.getPhotoList()
            }
            if (response.isSuccessful){ //응답 성공시
                response.body()!!.map { it.toDomain() }
            } else {
                Log.d("error: ", "error")
                emptyList()
            }
        } catch (e: Exception){
            Log.e("UnsplashRepository", "Failed to fetch photo list", e)
            emptyList()
        }
    }

    //fake
//    override suspend fun getPhotoListFake(): List<Photo> {
//        return unsplashDataSource.getPhotoListFake().map { it.toDomain() }
//    }


    override suspend fun getSearchPhotoList(query: String): List<Photo> {

        return try {
            val response = withContext(Dispatchers.IO){
                unsplashDataSource.getSearchPhoto(query)
            }
            if (response.isSuccessful){ //응답 성공시
                response.body()!!.map { it.toDomain() }
            } else {
                Log.d("error: ", "error")
                emptyList()
            }
        }catch (e: Exception){
            Log.e("UnsplashRepository", "Failed to fetch photo list", e)
            emptyList()
        }
    }
}