package com.example.finebyme.domain.usecase

import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.repositoryinterface.UserRepository
import javax.inject.Inject

class SetFavoritePhotoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun execute(isAdd: Boolean, photo: Photo) = userRepository.setFavoritePhoto(isAdd, photo)
}