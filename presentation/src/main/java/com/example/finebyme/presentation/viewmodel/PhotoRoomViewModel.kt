package com.example.finebyme.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//PhotoRoomViewModel : roomDB 에 저장되어 있는 데이터를 가져오기 위함
@HiltViewModel
class PhotoRoomViewModel @Inject constructor(
    private val getFavoritePhotoListUseCase: GetFavoritePhotoListUseCase
) : ViewModel() {    //AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
    private val _photoList = MutableLiveData<List<Photo>>()
    val photoData: LiveData<List<Photo>> = _photoList


    init {
        //FavoriteImgFragment 에서 호출하지 않고 바로 viewmodel 생성될때 호출
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            _photoList.value = getFavoritePhotoListUseCase.execute()
        }
    }

}