package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import javax.inject.Inject

class GetRandomPhotoListUseCase @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) {
    //fake
//    suspend fun execute() = unsplashRepository.getPhotoListFake()

    suspend fun execute() = unsplashRepository.getPhotoList()
}