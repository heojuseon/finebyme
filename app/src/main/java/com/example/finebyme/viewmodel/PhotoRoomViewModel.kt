package com.example.finebyme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import kotlinx.coroutines.launch

class PhotoRoomViewModel(private val roomRepository: PhotoRoomRepository): ViewModel() {    //AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐

    class Fa
    fun insert(photo: Photo) {
        viewModelScope.launch {
            roomRepository.insertPhoto(photo)
        }
    }

}

//ViewModel() 을 상속받아서 뷰모델 클래스를 생성할때는 ViewModelProvider.Factory 를 상속받는 Factory 클래스를 구현해야한다 -> viewModel 초기화 작업 필요
//AndroidViewModel() 을 상속받아서 구현할 수 있지만 방식이 달라짐
class PhotoRoomViewModelFactory (private val repository: PhotoRoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotoRoomViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}