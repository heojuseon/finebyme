package com.example.finebyme.viewmodel

import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import kotlinx.coroutines.launch

class PhotoFavoriteViewModel(private val roomRepository: PhotoRoomRepository) : ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _photo = MutableLiveData<PhotoData>()
    val photo: LiveData<PhotoData> = _photo


    fun onCreateViewModel(photoData: PhotoData, fromFavoriteScreen: Boolean) {
        _photo.value = photoData
        if (fromFavoriteScreen) {
            _isFavorite.value = true
        } else {
            _isFavorite.value = isFavoritePhoto(photoId = photoData.id)
            Log.d("_isFavorite.value: ", _isFavorite.value.toString())
        }
    }

    private fun isFavoritePhoto(photoId: String): Boolean {
        return roomRepository.isFavorite(photoId) > 0
    }


    fun isFavoriteCheck(selectedImage: PhotoData, isChecked: Boolean) {
        if (isChecked) {
            selectedImage.let {
                val photo = Photo(
                    id = it.id,
                    width = it.width,
                    height = it.height,
                    description = it.description,
                    altDescription = it.altDescription,
                    url = it.urls.regular
                )
                insertPhoto(photo)
            }
        } else {
            // 즐겨찾기 해제
            deletePhoto(selectedImage.id)
        }
    }

    private fun deletePhoto(photoId: String) {
        viewModelScope.launch {
            roomRepository.deletePhoto(photoId)
        }
    }

    private fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            roomRepository.insertPhoto(photo)
        }

    }
}

//ViewModel() 을 상속받아서 뷰모델 클래스를 생성할때는 ViewModelProvider.Factory 를 상속받는 Factory 클래스를 구현해야한다 -> viewModel 초기화 작업 필요
//AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
// ######### ViewModelProvider.Factory 를 사용함으로써 둘 이상의 Activity 간에 공유 가능 #########
    class PhotoFavoriteViewModelFactory(private val repository: PhotoRoomRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PhotoFavoriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PhotoFavoriteViewModel(repository) as T  // T 라는 타입으로 캐스트 하고 return(as : 자료형 변환)
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }