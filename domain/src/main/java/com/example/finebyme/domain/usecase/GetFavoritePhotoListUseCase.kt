package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.repositoryinterface.UserRepository
import javax.inject.Inject

class GetFavoritePhotoListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute() = userRepository.getFavoritePhotoList()
}