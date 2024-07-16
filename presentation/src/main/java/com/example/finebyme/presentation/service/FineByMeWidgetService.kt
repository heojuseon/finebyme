//package com.example.finebyme.presentation.service
//
//import android.app.Notification
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.app.Service
//import android.appwidget.AppWidgetManager
//import android.content.ComponentName
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.drawable.Drawable
//import android.os.Build
//import android.os.IBinder
//import android.util.Log
//import android.widget.RemoteViews
//import androidx.core.app.NotificationCompat
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.CustomTarget
//import com.bumptech.glide.request.transition.Transition
//import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
//import com.example.finebyme.presentation.R
//import com.example.finebyme.presentation.widget.FineByMeWidgetProvider
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
////import android.widget.RemoteViewsService
////import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
////import com.example.finebyme.presentation.widget.FineByMeWidgetFactory
////import dagger.hilt.android.AndroidEntryPoint
////import javax.inject.Inject
//
//////@AndroidEntryPoint
//////위젯에 리스트형태의 데이터를 보여주기 위해서는 RemoteViewsService 를 사용
////class FineByMeWidgetService: RemoteViewsService() {
////
//////    @Inject
//////    lateinit var getFavoritePhotoListUseCase: GetFavoritePhotoListUseCase
////
////    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
//////        return FineByMeWidgetFactory(this.applicationContext, getFavoritePhotoListUseCase)
////
////        val widgetList = intent?.getStringArrayListExtra("widgetList") ?: emptyList<String>()
////
//////        return FineByMeWidgetFactory(this.applicationContext)
////        return FineByMeWidgetFactory(this.applicationContext, widgetList)
////    }
////}
//
//@AndroidEntryPoint
//class FineByMeWidgetService: Service() {
//
//    private var currentPhotoPosition = 0
//
//    @Inject
//    lateinit var getFavoritePhotoListUseCase: GetFavoritePhotoListUseCase
//
//
//    // 서비스가 가동될 때 호출되는 메서드, 해당 서비스클래스의 인스턴스 객체 생성
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        if (intent?.action == "com.example.finebyme.presentation.widget.ACTION_UPDATE_WIDGET") {
//            updateWidget()
//        }
//        startForeground(1, createNotification())    // Foreground Service로 전환
//        return START_STICKY
//    }
//
//
//    private fun updateWidget() {
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val widgetPhotoUrl = getFavoritePhotoListUseCase.execute()
//
//                withContext(Dispatchers.Main){
//                    val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
//                    val componentName = ComponentName(applicationContext, FineByMeWidgetProvider::class.java)
//                    val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
//
//                    //현재 포지션 이미지 url 가져오기
//                    val currentPhotoUrl = widgetPhotoUrl[currentPhotoPosition].fullUrl
//
//                    appWidgetIds.forEach { appWidgetId ->
//                        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.finebyme_widget_layout)
//                        loadImageIntoRemoteViews(applicationContext, currentPhotoUrl, remoteViews, appWidgetId, appWidgetManager)
////                        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
//                    }
//
//                    //다음 포지션으로 이동(마지막 포지션인 경우 0으로 이동)
//                    currentPhotoPosition = (currentPhotoPosition + 1) % widgetPhotoUrl.size
//                    Log.d("!@#!@#", "service_currentPhotoPosition: $currentPhotoPosition")
//                }
//
//            } catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun loadImageIntoRemoteViews(
//        context: Context,
//        fullUrl: String,
//        remoteViews: RemoteViews,
//        appWidgetId: Int,
//        appWidgetManager: AppWidgetManager
//    ) {
//        val targetWidth = 250 // 원하는 너비
//        val targetHeight = 250 // 원하는 높이
//
//        Glide.with(context)
//            .asBitmap()
//            .load(fullUrl)
//            .centerCrop()
//            .override(targetWidth, targetHeight)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    remoteViews.setImageViewBitmap(R.id.widget_photoImageView, resource)
//                    appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//
//
//    private fun createNotification(): Notification {
//        val notificationChannelId = "fine_by_me_widget_channel"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                notificationChannelId,
//                "Widget Update Service",
//                NotificationManager.IMPORTANCE_LOW
//            )
//
//            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//
//        return NotificationCompat.Builder(this, notificationChannelId)
//            .setContentTitle("Widget Update Service")
//            .setContentText("Updating widget data...")
//            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .build()
//    }
//
//}