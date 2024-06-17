package com.example.finebyme.di.modules

import android.content.Context
import androidx.room.Room
import com.example.finebyme.data.db.dao.PhotoDAO
import com.example.finebyme.data.db.database.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//추상 클래스는 바로 inject 해서 쓸 수 없다. hilt가 추상 클래스가 어디에 구현되어 있는지 모르기 때문
//따라서 Module을 만들어서 구현해야 hilt가 구현부를 찾아 갈 수 있다.
@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides //Moudle을 구현하는 방법에는 Provides와 Bidns 두가지가 있다. 외부라이브러리에는 Binds를 못쓴다.
    @Singleton //module을 싱글톤으로 만들겠다.
    fun provideAppDataBase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo.db"
        )
            //Main Thread에서 DB에 입출력을 가능하게 함 -> 없을 경우 FavoriteFragment 에서 이미지 못가져옴
            //error : Cannot access database on the main thread since it may potentially lock the UI for a long period of time.
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
//    fun providePhotoDao(photoDatabase: PhotoDatabase) = photoDatabase.photoDao()
    fun providePhotoDao(photoDatabase: PhotoDatabase): PhotoDAO {
        return photoDatabase.photoDao()
    }
}