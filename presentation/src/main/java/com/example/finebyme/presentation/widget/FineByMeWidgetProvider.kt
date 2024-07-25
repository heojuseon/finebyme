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
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
import com.example.finebyme.presentation.R
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

    /*
        PendingIntent 객체 생성하여 Flag 값을 통해 액티비티를 띄울때 화면을 갱신해서 띄우고 싶을때는 FLAG_UPDATE_CURRENT
        를 사용하지만 12 이상부터는 FLAG_MUTABLE 사용을 해야해서 분기처리
     */
    private val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)

        Log.d("!@#!@#", "getAction: ${intent?.action}")

        when (intent?.action) {
            "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET",
            "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET_APP",
            "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET_ALARAM"  -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val componentName = ComponentName(context, FineByMeWidgetProvider::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)

                updateWidget(context, appWidgetManager, appWidgetIds)
            }


            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("!@#!@#", "boot: ${intent.action}")
                setAlarm(context)
            }
        }

    }

    private fun setAlarm(context: Context) {
        //AlarmManager
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager  //as : AlarmManager 타입지정
        val fromBootIntent = Intent(context, FineByMeWidgetProvider::class.java).apply {  // 알람 발생 시 실행될 intent
            action = "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET_ALARAM"
        }

        //PendingIntent: 보류 인텐트 -> 지금 당장 인텐트를 실행하는 것이 아닌 특정 시간에 인텐트를 실행시킬 수 있도록 도와주는 객체
//        val pendingIntent = PendingIntent.getBroadcast(context, 0, fromBootIntent, PendingIntent.FLAG_MUTABLE)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, fromBootIntent, pendingIntentFlag)

        //다시 알람을 설정할때는 취소 후 다시 알람 1분 설정
        alarmManager.cancel(pendingIntent)

        /*
            Doze 모드? : 기기를 오랫동안 사용하지 않는 경우 앱의 백그라운드 CPU 및 네트워크 활동을 지연시켜 배터리 소모를 줄여주는 모드
                        - 사용자가 전원을 충전하지 않고 화면이 꺼진 채로 기기를 일정 기간 정지 상태로 두면 기기는 Doze 모드를 시작
            ex) setExactAndAllowWhileIdle() : Doze 모드에서도 실행되는 알람을 설정해야 하는 경우 사용
         */
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 60_000,
            pendingIntent

        )
        Log.d("!@#!@#", "Setting alarm with: ${SystemClock.elapsedRealtime() + 60_000} ----> ${System.currentTimeMillis()}")

    }


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.d("!@#!@#", "onUpdate: $context + $appWidgetManager + $appWidgetIds")

        updateWidget(context, appWidgetManager, appWidgetIds)
    }


    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        setAlarm(context)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val widgetPhotoUrl = getFavoritePhotoListUseCase.execute()

                withContext(Dispatchers.Main){
                    appWidgetIds.forEach { appWidgetId ->
                        Log.d("!@#!@#", "updateWidget appWidgetId : $appWidgetId")

                        val remoteViews = RemoteViews(context.packageName, R.layout.finebyme_widget_layout)

                        if (widgetPhotoUrl.isEmpty()){
                            remoteViews.setViewVisibility(R.id.widget_photoImageView, View.GONE)
                            remoteViews.setViewVisibility(R.id.image_null, View.VISIBLE)
                        } else {
                            var currentPhotoPosition = getCurrentPhotoPosition(context)

                            /*
                            room DB에 position[0, 1, 2] -> 3개의 이미지가 저장되어있을때 현재 위젯에 보여지고 있는 이미지가
                            position 1 의 값이 해당될때 position 1번을 삭제할 경우 1분뒤 getCurrentPhotoPosition 의 값은 +1 이
                            되어 2가 되지만 roomDB에 저장되어있는 widgetPhotoUrl 의 사이즈와 같아 오류가 발생
                            */
                            if (currentPhotoPosition >= widgetPhotoUrl.size) {
                                currentPhotoPosition = 0
                            }

                            val currentPhotoUrl = widgetPhotoUrl[currentPhotoPosition].fullUrl
                            Log.d("!@#!@#", "getCurrentPhotoPosition: $currentPhotoPosition")

                            loadImageIntoRemoteViews(context, currentPhotoUrl, remoteViews, appWidgetId, appWidgetManager)

                            remoteViews.setViewVisibility(R.id.widget_photoImageView, View.VISIBLE)
                            remoteViews.setViewVisibility(R.id.image_null, View.GONE)

                            saveCurrentPhotoPosition(context, (currentPhotoPosition + 1) % widgetPhotoUrl.size)
                        }

                        val clickIntent = Intent()
                            .setClassName(context.packageName, "com.example.finebyme.MainActivity")
                            .apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
//                        val pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_MUTABLE)
                        val pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, pendingIntentFlag)
                        remoteViews.setOnClickPendingIntent(R.id.root_widget_layout, pendingIntent)

                        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
                    }
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
        Log.d("!@#!@#", "saveCurrentPhotoPosition: $position")
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
        Log.d("!@#!@#", "onEnabled()")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        //마지막의 최종 앱 위젯 인스턴스가 삭제 될 때 호출
        //ex) 동일한 finebyme 위젯을 여러개 올라가져 있을때 마지막 finebyme 위젯을 삭제 할때 호출(이때 각 위젯은 서로 다른 인스턴스 가짐)
        Log.d("!@#!@#", "onDisabled()")
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
    }
}