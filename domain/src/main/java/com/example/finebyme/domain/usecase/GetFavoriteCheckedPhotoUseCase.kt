package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.repositoryinterface.UserRepository
import javax.inject.Inject

class GetFavoriteCheckedPhotoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(potoId: String) = userRepository.isFavoritePhoto(potoId)
}