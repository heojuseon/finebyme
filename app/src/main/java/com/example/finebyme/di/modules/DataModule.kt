package com.example.finebyme.di.modules

import com.example.finebyme.data.datasource.UnsplashDataSource
import com.example.finebyme.data.datasource.UnsplashDataSourceFake
import com.example.finebyme.data.repository.UnsplashRepositoryImpl
import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUnsplashDataSource(): UnsplashDataSource {
        return UnsplashDataSourceFake()
    }

    @Provides
    @Singleton
    fun provideUnsplashRepository(unsplashDataSource: UnsplashDataSource): UnsplashRepository {
        return UnsplashRepositoryImpl(unsplashDataSource)
    }
}