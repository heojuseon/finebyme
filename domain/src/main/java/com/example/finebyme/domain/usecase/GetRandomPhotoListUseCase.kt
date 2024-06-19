package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import javax.inject.Inject

class GetRandomPhotoListUseCase @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) {
    suspend fun execute() = unsplashRepository.getPhotoList()
}