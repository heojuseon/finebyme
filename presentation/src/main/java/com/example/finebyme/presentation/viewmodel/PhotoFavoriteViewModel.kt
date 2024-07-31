package com.example.finebyme.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.usecase.GetFavoriteCheckedPhotoUseCase
import com.example.finebyme.domain.usecase.SetFavoritePhotoUseCase
import com.example.finebyme.presentation.utill.ImageDownload
import com.example.finebyme.presentation.widget.FineByMeWidgetProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoFavoriteViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val setFavoritePhotoUseCase: SetFavoritePhotoUseCase,
    private val getFavoriteCheckedPhotoUseCase: GetFavoriteCheckedPhotoUseCase
) : ViewModel() {

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo

    fun onCreateViewModel(photo: Photo, fromFavoriteScreen: Boolean) {
        _photo.value = photo
        if (fromFavoriteScreen) {
            _isFavorite.value = true
        } else {
            _isFavorite.value = isFavoritePhoto(photoId = photo.id)
            Log.d("_isFavorite.value: ", _isFavorite.value.toString())
        }
    }

    private fun isFavoritePhoto(photoId: String): Boolean {
        return getFavoriteCheckedPhotoUseCase.execute(photoId) > 0
    }

    fun tapPhotoLike(){
        if (_isFavorite.value == true) {
            _photo.value?.let {
                val photo = Photo(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    description = it.description,
                    altDescription = it.altDescription,
                    thumbUrl = it.thumbUrl,
                    fullUrl = it.fullUrl
                )
                deletePhoto(photo)
                _isFavorite.value = false
            }
        } else {
            _photo.value?.let {
                val photo = Photo(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    description = it.description,
                    altDescription = it.altDescription,
                    thumbUrl = it.thumbUrl,
                    fullUrl = it.fullUrl
                )
                insertPhoto(photo)
                _isFavorite.value = true
            }
        }
    }
    private fun deletePhoto(photo: Photo) {
        viewModelScope.launch {
            setFavoritePhotoUseCase.execute(false, photo)
            Log.d("!@#!@#", "deletePhoto()")

            val intent = Intent(context, FineByMeWidgetProvider::class.java)
            intent.action = "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET_APP"
            context.sendBroadcast(intent)
        }
    }

    private fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            setFavoritePhotoUseCase.execute(true, photo)
            Log.d("!@#!@#", "insertPhoto()")

            val intent = Intent(context, FineByMeWidgetProvider::class.java)
            intent.action = "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET_APP"
            context.sendBroadcast(intent)
        }

    }

    //ViewModel에서 Context를 직접 참조하는 것은 메모리 누수를 초래할 수 있으므로 권장되지 않음
    fun downloadImage(context: Context?) {
        val downloadUrl = _photo.value?.fullUrl
        ImageDownload.down(context, downloadUrl)
    }
}