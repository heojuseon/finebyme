package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import javax.inject.Inject

class GetSearchPhotoListUseCase @Inject constructor(
    private val unsplashRepository: UnsplashRepository
) {
    suspend fun execute(query: String) = unsplashRepository.getSearchPhotoList(query)
}