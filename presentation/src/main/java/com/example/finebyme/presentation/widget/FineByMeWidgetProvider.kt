package com.example.finebyme.presentation.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
import com.example.finebyme.presentation.R
import com.example.finebyme.presentation.view.FavoriteImgFragment
//import com.example.finebyme.presentation.service.FineByMeWidgetService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
   - AppWidgetProvider 클래스는 위젯 브로드캐스트를 처리하기 위한 편의 클래스 -> BroadCastReceiver 확장
   - 위젯이 업데이트, 삭제, 사용 설정, 사용 중지될 때와 같이 위젯과 관련된 이벤트 브로드캐스트만 수신
   - 위젯 브로드캐스트를 직접 수신하려면 자체 BroadcastReceiver를 구현하거나 onReceive(Context,Intent) 콜백을 재정의
 */
@AndroidEntryPoint
class FineByMeWidgetProvider: AppWidgetProvider() {

    @Inject
    lateinit var getFavoritePhotoListUseCase: GetFavoritePhotoListUseCase

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        when (intent?.action) {
            "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET" -> {
                Log.d("!@#!@#", "getAction: ${intent.action}")
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val componentName = ComponentName(context, FineByMeWidgetProvider::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

                updateWidget(context, appWidgetManager, appWidgetIds)
            }

            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("!@#!@#", "boot: ${intent.action}")
                //AlarmManager
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager  //as : AlarmManager 타입지정
                val fromBootIntent = Intent(context, FineByMeWidgetProvider::class.java).apply {  // 알람 발생 시 실행될 intent
                    action = "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET"
                }

                //PendingIntent: 보류 인텐트 -> 지금 당장 인텐트를 실행하는 것이 아닌 특정 시간에 인텐트를 실행시킬 수 있도록 도와주는 객체
                val pendingIntent = PendingIntent.getBroadcast(context, 0, fromBootIntent, PendingIntent.FLAG_MUTABLE)

                alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP, //실제 시간 기준
                    AlarmManager.ELAPSED_REALTIME_WAKEUP, //기기가 부팅된 후 경과한 시간 기준
                    SystemClock.elapsedRealtime() + 10_000, // 10초 후 시작
                    60_000,
                    //alarmmanager 가 등록된 후 1분 후 부터 1분 간격으로 알림 발생
//            SystemClock.elapsedRealtime() + 10000,
//            60000,
                    pendingIntent
                )
            }
        }

    }


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d("!@#!@#", "onUpdate: $context + $appWidgetManager + $appWidgetIds")

        updateWidget(context, appWidgetManager, appWidgetIds)

        //AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager  //as : AlarmManager 타입지정
        val intent = Intent(context, FineByMeWidgetProvider::class.java).apply {  // 알람 발생 시 실행될 intent
            action = "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET"
        }

        //PendingIntent: 보류 인텐트 -> 지금 당장 인텐트를 실행하는 것이 아닌 특정 시간에 인텐트를 실행시킬 수 있도록 도와주는 객체
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)

        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP, //실제 시간 기준
            AlarmManager.ELAPSED_REALTIME_WAKEUP, //기기가 부팅된 후 경과한 시간 기준
            SystemClock.elapsedRealtime() + 10000, // 10초 후 시작
            60000,
            //alarmmanager 가 등록된 후 1분 후 부터 1분 간격으로 알림 발생
//            SystemClock.elapsedRealtime() + 10000,
//            60000,
            pendingIntent
        )
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val widgetPhotoUrl = getFavoritePhotoListUseCase.execute()

                withContext(Dispatchers.Main){

                    //현재 포지션 이미지 url 가져오기
                    val currentPhotoPosition = getCurrentPhotoPosition(context)
                    val currentPhotoUrl = widgetPhotoUrl[currentPhotoPosition].fullUrl

                    appWidgetIds.forEach { appWidgetId ->
                        val remoteViews = RemoteViews(context.packageName, R.layout.finebyme_widget_layout)
                        loadImageIntoRemoteViews(context, currentPhotoUrl, remoteViews, appWidgetId, appWidgetManager)

                        // 클릭 시 실행될 인텐트 정의
                        val clickIntent = Intent()
                            .setClassName(context.packageName, "com.example.finebyme.MainActivity")
                            .apply {
                                //FLAG_ACTIVITY_CLEAR_TOP : 호출하는 Activity가 스택에 있을 경우, 해당 Activity를 최상위로 올리면서, 그 위에 있던 Activity들을 모두 삭제하는 Flag
                                //FLAG_ACTIVITY_SINGLE_TOP : 호출되는 Activity가 최상위에 있을 경우 해당 Activity를 다시 생성하지 않고, 있던 Activity를 다시 사용
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
                        val pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_MUTABLE)
                        remoteViews.setOnClickPendingIntent(R.id.root_widget_layout, pendingIntent)
                    }

                    //다음 포지션으로 이동(마지막 포지션인 경우 0으로 이동)
//                    currentPhotoPosition = (currentPhotoPosition + 1) % widgetPhotoUrl.size
                    saveCurrentPhotoPosition(context, (currentPhotoPosition + 1) % widgetPhotoUrl.size)
                    Log.d("!@#!@#", "currentPhotoPosition: $currentPhotoPosition")
                }

            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getCurrentPhotoPosition(context: Context): Int {
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE)
        return prefs.getInt("currentPhotoPosition", 0)
    }

    private fun saveCurrentPhotoPosition(context: Context, position: Int) {
        val prefs = context.getSharedPreferences("widget_prefs", Context.MODE_PRIVATE).edit()
        prefs.putInt("currentPhotoPosition", position).apply()
    }


    private fun loadImageIntoRemoteViews(
        context: Context,
        fullUrl: String,
        remoteViews: RemoteViews,
        appWidgetId: Int,
        appWidgetManager: AppWidgetManager
    ) {
        val targetWidth = 200 // 원하는 너비
        val targetHeight = 200 // 원하는 높이

        Glide.with(context)
            .asBitmap()
            .load(fullUrl)
            .centerCrop()
            .override(targetWidth, targetHeight)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    remoteViews.setImageViewBitmap(R.id.widget_photoImageView, resource)
                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
    }


    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)

        //앱 위젯이 등록 될 때와 앱 위젯의 크기가 변경 될 때 호출 됩니다.
        //이때, Bundle 에 위젯 너비/높이의 상한값/하한값 정보를 넘겨주며 이를 통해 컨텐츠를 표시하거나 숨기는 등의 동작을 구현
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        //앱 위젯은 여러개가 등록 될 수 있는데, 최초의 앱 위젯이 등록 될 때 호출
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        //마지막의 최종 앱 위젯 인스턴스가 삭제 될 때 호출
        //ex) 동일한 finebyme 위젯을 여러개 올라가져 있을때 마지막 finebyme 위젯을 삭제 할때 호출(이때 각 위젯은 서로 다른 인스턴스 가짐)
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        //앱 데이터가 구글 시스템에 백업 된 이후 복원 될 때 만약 위젯 데이터가 있다면 데이터가 복구 된 이후 호출
        //일반적으로 사용 될 경우는 흔치 않습니다.
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        //해당 앱 위젯이 삭제 될 때 호출
        Log.d("!@#!@#", "onDeleted()")

        //위젯이 삭제 될때 alarmManager 해제
        val intent = Intent(context, FineByMeWidgetProvider::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        Log.d("!@#!@#", "alarmManager_cancel: $pendingIntent")
    }
}