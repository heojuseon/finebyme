package com.example.finebyme.presentation.utill

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ImageDownload {

    fun down(context: Context?, downloadUrl: String?) {
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