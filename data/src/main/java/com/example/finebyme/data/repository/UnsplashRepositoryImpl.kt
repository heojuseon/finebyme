package com.example.finebyme.data.repository

import com.example.finebyme.data.datasource.UnsplashDataSource
import com.example.finebyme.data.dto.UnsplashPhoto
import com.example.finebyme.data.dto.mapper.PhotoMapper.toDomain
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val unsplashDataSource: UnsplashDataSource
): UnsplashRepository {
    override suspend fun getPhotoList(): List<Photo> {
        return unsplashDataSource.getPhotoList().map { it.toDomain() }
    }

    override suspend fun getSearchPhotoList(query: String): List<Photo> {

        return emptyList()
    }
}