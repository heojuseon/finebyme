package com.example.finebyme.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.entity.PhotoMapper
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import kotlinx.coroutines.launch

class PhotoRoomViewModel(private val roomRepository: PhotoRoomRepository): ViewModel() {    //AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
    private val _photoList = MutableLiveData<List<PhotoData>>()
    val photoData: LiveData<List<PhotoData>> = _photoList
    init {
        //FavoriteImgFragment 에서 호출하지 않고 바로 viewmodel 생성될때 호출
        getAll()
    }

    fun insert(photo: Photo) {
        viewModelScope.launch {
            roomRepository.insertPhoto(photo)
        }
    }

    private fun getAll() {
        _photoList.value = roomRepository.getAllPhoto().map {
            PhotoMapper.convertPhotoData(it)
        }
    }

}

//ViewModel() 을 상속받아서 뷰모델 클래스를 생성할때는 ViewModelProvider.Factory 를 상속받는 Factory 클래스를 구현해야한다 -> viewModel 초기화 작업 필요
//AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
// ######### ViewModelProvider.Factory 를 사용함으로써 둘 이상의 Activity 간에 공유 가능 #########
class PhotoRoomViewModelFactory (private val repository: PhotoRoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoRoomViewModel(repository) as T  // T 라는 타입으로 캐스트 하고 return(as : 자료형 변환)
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}