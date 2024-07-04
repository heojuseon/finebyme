package com.example.finebyme.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.usecase.GetRandomPhotoListUseCase
import com.example.finebyme.domain.usecase.GetSearchPhotoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getRandomPhotoListUseCase: GetRandomPhotoListUseCase,
    private val getSearchPhotoListUseCase: GetSearchPhotoListUseCase
): ViewModel() {
    //클린아키텍쳐

    //MutableLiveData :
    //LiveData 서브 클래스, 값을 변경 가능(setValue() : 메인스레드에서 값 변경 / postValue() : 백그라운드에서 값 변경)
    //ViewModel 내에서 데이터를 업데이트할 때 사용
    private val _photoList = MutableLiveData<List<Photo>>()

    //LiveData:
    //데이터의 변경을 관찰자(주로 View)에게 알리는 역할
    //외부에서 데이터 수정 불가능
    val photoData: LiveData<List<Photo>> = _photoList


    //ErrorHandling(Live 데이터로 관찰)
    private val _ephotoList = MutableLiveData<Result<List<Photo>>>()
    val ephotoData: LiveData<Result<List<Photo>>> = _ephotoList


    private val _query = MutableLiveData<String>("")
    val query: LiveData<String> = _query
    fun searchImg(query: String) {
        _query.value = query
        searchScope(query)
    }

    init {
        photoScope()
    }

    private fun searchScope(query: String) {
        viewModelScope.launch {
//            _photoList.value = getSearchPhotoListUseCase.execute(query)

            //error 처리 추가
            _ephotoList.value = getSearchPhotoListUseCase.execute(query)
        }
    }


    private fun photoScope() {

        viewModelScope.launch {
//            _photoList.value = getRandomPhotoListUseCase.execute()

            //error 처리 추가
            _ephotoList.value = getRandomPhotoListUseCase.execute()
        }
    }
}