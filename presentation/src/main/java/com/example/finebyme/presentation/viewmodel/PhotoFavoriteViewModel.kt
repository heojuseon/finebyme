package com.example.finebyme.presentation.viewmodel

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.domain.usecase.GetFavoriteCheckedPhotoUseCase
import com.example.finebyme.domain.usecase.SetFavoritePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PhotoFavoriteViewModel @Inject constructor(
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
        }
    }

    private fun insertPhoto(photo: Photo) {
        viewModelScope.launch {
            setFavoritePhotoUseCase.execute(true, photo)
        }

    }

    //ViewModel에서 Context를 직접 참조하는 것은 메모리 누수를 초래할 수 있으므로 권장되지 않음
//    fun downloadImage() {
    fun downloadImage(context: Context?) {
        val downloadUrl = _photo.value?.fullUrl
        Log.d("!@!@!@", "downloadUrl: $downloadUrl")

        try {
            //파일 저장 명칭 지정
            val currentTimeMillis = System.currentTimeMillis()
            val simpleDateFormat = SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault())
            val fileName = simpleDateFormat.format(Date(currentTimeMillis)) + ".jpg"

            //다운로드 매니저 선언 및 파일 다운로드
            val manager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            //다운로드 매니저 객체 생성
            val request =
                DownloadManager.Request(Uri.parse(downloadUrl!!.trim { it <= ' ' }))  //파일 다운로드 주소 : 확장자명 포함, trim : 문자열 합칠 경우 공백 제거
            request.apply {
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)  //앱 상단에 다운로드 표시
                setTitle(fileName)  //다운로드 제목 표시
                setDescription("Download...")   //다운로드 중 표시되는 내용
                setNotificationVisibility(1)    //앱 상단에 다운로드 상태 표시
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    fileName
                ) //다운로드 폴더 지정 : 갤러리
                setAllowedOverMetered(true) //네트워크가 연결 된 경우에도 다운로드
            }
            manager.enqueue(request)    //다운로드 수행

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}