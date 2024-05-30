package com.example.finebyme.data.db.entity

import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.data.remote.model.Urls

object PhotoMapper {

    //Photo -> PhotoData
    fun convertPhotoData(photo: Photo): PhotoData{
        return PhotoData(
            id = photo.id,
            width = photo.width,
            height = photo.height,
            description = photo.description,
            altDescription = photo.altDescription,
            urls = Urls(
                raw = photo.url,
                full = photo.url,
                regular = photo.url,
                small = photo.url,
                thumb = photo.url,
                small_s3 = photo.url
            )
        )
    }

}