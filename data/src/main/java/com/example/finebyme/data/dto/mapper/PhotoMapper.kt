package com.example.finebyme.data.dto.mapper

import com.example.finebyme.data.dto.UnsplashPhoto
import com.example.finebyme.domain.entity.Photo

object PhotoMapper {

    // UnsplashPhoto 에 확장함수를 구현해서 Domain Layer에서 정의한 Photo로 변환하는 확장함수도 구현
    //Data Layer는 Domain Layer에 의존하고 있기 때문에 Photo 를 불러올 수 있게 됩니다.
    fun UnsplashPhoto.toDomain() = Photo(
        id = id,
        width = width,
        height = height,
        description = description,
        altDescription = altDescription,
        thumbUrl = thumbUrl,
        fullUrl = fullUrl
    )
}