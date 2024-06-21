package com.example.finebyme.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//PhotoRoomViewModel : roomDB 에 저장되어 있는 데이터를 가져오기 위함
@HiltViewModel
class PhotoRoomViewModel @Inject constructor(private val roomRepository: PhotoRoomRepository): ViewModel() {    //AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
    private val _photoList = MutableLiveData<List<PhotoData>>()
    val photoData: LiveData<List<PhotoData>> = _photoList


    init {
        //FavoriteImgFragment 에서 호출하지 않고 바로 viewmodel 생성될때 호출
        getAll()
    }

    private fun getAll() {
        _photoList.value = roomRepository.getAllPhoto().map {
            PhotoMapper.convertPhotoData(it)    //Room DB에 사용하는 데이터 클래스는 Photo 지만 PhotoData로 매핑하여 사용
        }
    }

}