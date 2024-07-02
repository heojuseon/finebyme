package com.example.finebyme.di.modules

import com.example.finebyme.data.datasource.UnsplashDataSource
import com.example.finebyme.data.datasource.UnsplashDataSourceFake
import com.example.finebyme.data.datasource.UnsplashDataSourceImpl
import com.example.finebyme.data.datasource.UserDataSource
import com.example.finebyme.data.datasource.UserDataSourceImpl
import com.example.finebyme.data.datasource.db.FavoritePhotoDAO
import com.example.finebyme.data.datasource.service.UnsplashAPI
import com.example.finebyme.data.repository.UnsplashRepositoryImpl
import com.example.finebyme.data.repository.UserRepositoryImpl
import com.example.finebyme.domain.repositoryinterface.UnsplashRepository
import com.example.finebyme.domain.repositoryinterface.UserRepository
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
    fun provideUnsplashDataSource(unsplashAPI: UnsplashAPI): UnsplashDataSource {
//        return UnsplashDataSourceFake()
        return UnsplashDataSourceImpl(unsplashAPI)
    }

    @Provides
    @Singleton
    fun provideUserDataSource(favoritePhotoDAO: FavoritePhotoDAO): UserDataSource {
        return UserDataSourceImpl(favoritePhotoDAO)
    }

    @Provides
    @Singleton
    fun provideUnsplashRepository(unsplashDataSource: UnsplashDataSource): UnsplashRepository {
        return UnsplashRepositoryImpl(unsplashDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }

}