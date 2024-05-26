package com.example.finebyme.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.data.remote.model.PhotoData
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

    private val _selectedPosition = MutableLiveData<Int>()
    val selectedPosition: LiveData<Int> = _selectedPosition


    fun sendData(position: Int, photoList: List<PhotoData>) {
        _selectedPosition.value = position
        _photoList.value = photoList
    }

    init {
        photoScope()
    }


    private fun photoScope() {
        //        //생명주기를 인식하여 한전하게 코루틴 사용
//        lifecycleScope.launch {
//            try {
//                val phoList = withContext(Dispatchers.IO){
//                    PhotoRepository.unsplashApi.getPhotoList()
//                }
//                getPhotos(phoList)
//
//            } catch (e: Exception){
//                e.printStackTrace()
//                Toast.makeText(requireContext(), "Error fetching photos", Toast.LENGTH_SHORT).show()
//            }
//        }

        //response 함수 사용하지 않을경우
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val phoList = withContext(Dispatchers.IO) {
//                    PhotoRepository.unsplashApi.getPhotoList()
//                }
//                getPhotos(phoList)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(requireContext(), "Error fetching photos", Toast.LENGTH_SHORT).show()
//            }
//        }

               //response 함수 사용
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    PhotoRepository.unsplashApi.getPhotoList()
//                }
//                if (response.isSuccessful) {    //응답 성공시
//                    val photoList = response.body()
//                    if (photoList != null) {
//                        getPhotos(photoList)
//                    } else {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(requireContext(), "No photos found", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                } else {   //응답 실패시
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(requireContext(), "Error fetching photos", Toast.LENGTH_SHORT).show()
//            }
//        }

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
