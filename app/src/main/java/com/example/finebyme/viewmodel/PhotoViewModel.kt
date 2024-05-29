package com.example.finebyme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.db.entity.PhotoData
import com.example.finebyme.data.remote.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class PhotoViewModel: ViewModel() {

    //MutableLiveData :
    //LiveData 서브 클래스, 값을 변경 가능(setValue() : 메인스레드에서 값 변경 / postValue() : 백그라운드에서 값 변경)
    //ViewModel 내에서 데이터를 업데이트할 때 사용
    private val _photoList = MutableLiveData<List<PhotoData>>()

    //LiveData:
    //데이터의 변경을 관찰자(주로 View)에게 알리는 역할
    //외부에서 데이터 수정 불가능
    val photoData: LiveData<List<PhotoData>> = _photoList


    //리스트화면에서 상세화면으로 데이터를 전달할 viewmodel 생성(해당 포지션 값에 대한 이미지 데이터 전달)
    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> = _selectedPosition

    //어뎁터를 클릭했을때 해당 position 과 이미지 리스트를 담을 sendData 함수 생성
    fun sendData(position: Int, photoList: List<PhotoData>) {
        _selectedPosition.value = position
        _photoList.value = photoList
    }

    init {
        photoScope()
    }


    private fun photoScope() {

        //viewModelScope(dependencies 추가)
        //ViewModelScope에는 Dispatchers.Main이 기본
        //viewModelScope => ViewModel 수명과 함께 자동으로 관리되는 코루틴 스코프
        viewModelScope.launch { //코루틴 시작
            try {
                //withContext : 네트워크 요청을 백그라운드 스레드에서 수행 -> 네트워크 요청과 같은 I/O 작업을 메인 스레드가 아닌 다른 스레드에서 실행
                val response = withContext(Dispatchers.IO) {
                    PhotoRepository.unsplashApi.getPhotoList()
                }
                if (response.isSuccessful) {    //응답 성공시
                    _photoList.postValue(response.body())
                } else {   //응답 실패시
                    withContext(Dispatchers.Main) {
                        Log.d("error: ", "error")
                    }
                }

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
