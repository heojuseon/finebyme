package com.example.finebyme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import kotlinx.coroutines.launch

class PhotoRoomViewModel(private val roomRepository: PhotoRoomRepository): ViewModel() {

    fun insert(photo: Photo) = viewModelScope.launch {
        roomRepository.insertPhoto(photo)
    }
}