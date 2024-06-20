package com.example.finebyme.data.datasource

import com.example.finebyme.data.dto.UnsplashPhoto
import javax.inject.Inject

class UnsplashDataSourceFake : UnsplashDataSource {

    override suspend fun getPhotoList(): List<UnsplashPhoto> {
        return listOf(
            UnsplashPhoto(
                id = "1",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "2",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "3",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "4",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "5",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "6",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "7",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "8",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "9",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            ),
            UnsplashPhoto(
                id = "10",
                width = 1920,
                height = 1080,
                description = "fake",
                altDescription = "fake",
                thumbUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=80&w=200",
                fullUrl = "https://images.unsplash.com/photo-1715706107718-4a0cc4f0335c?crop=entropy&cs=srgb&fm=jpg&ixid=M3w1OTkyNjJ8MHwxfHJhbmRvbXx8fHx8fHx8fDE3MTg4NDY2MDd8&ixlib=rb-4.0.3&q=85"
            )
        )
    }

}