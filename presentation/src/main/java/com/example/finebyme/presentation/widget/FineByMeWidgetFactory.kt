//package com.example.finebyme.presentation.widget
//
//import android.content.Context
//import android.net.Uri
//import android.util.Log
//import android.widget.ImageView
//import android.widget.RemoteViews
//import android.widget.RemoteViewsService.RemoteViewsFactory
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.engine.DiskCacheStrategy
//import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
//import com.bumptech.glide.request.RequestOptions
//import com.bumptech.glide.request.target.Target
//import com.example.finebyme.domain.entity.Fake
//import com.example.finebyme.domain.entity.Photo
//import com.example.finebyme.domain.usecase.GetFavoritePhotoListUseCase
//import com.example.finebyme.presentation.R
//import kotlinx.coroutines.runBlocking
//import javax.inject.Inject
//
//
//
////service 클래스에서 RemoteViewsService 를 상속 받을 때 Factory를 구현하여 ListView 또는 GridView와 같은 컬렉션 뷰에서 데이터 소스를 제공
//class FineByMeWidgetFactory(
//    private val context: Context,
//    private var widgetList: List<String>
//): RemoteViewsFactory {
//
////    private var widgetPhotoList: List<Photo> = arrayListOf()
//
////    private var fakeList: ArrayList<Fake> = arrayListOf()
//
//
////    private fun loadData() {
////        runBlocking {
////            widgetPhotoList = getFavoritePhotoListUseCase.execute()
////        }
////    }
//
////    private fun loadData() {
////        fakeList.clear()
////        fakeList.add(Fake(1, "First"))
////        fakeList.add(Fake(2, "Second"))
////        fakeList.add(Fake(3, "Third"))
////        fakeList.add(Fake(4, "Fourth"))
////        fakeList.add(Fake(5, "Fifth"))
////
////    }
//
//    override fun onCreate() {
//        Log.d("FineByMeWidgetFactory", "onCreate()")
////        loadData()
//    }
//
//
//    //항목 추가 및 제거 등 데이터 변경이 발생했을 때 호출되는 함수
//    //브로드캐스트 리시버에서 notifyAppWidgetViewDataChanged()가 호출 될 때 자동 호출
//    override fun onDataSetChanged() {
////        loadData()
//    }
//
//    override fun onDestroy() {
//    }
//
//    override fun getCount(): Int {
////        Log.d("FineByMeWidgetFactory", "size: ${widgetPhotoList.size}")
////        return widgetPhotoList.size
//        Log.d("FineByMeWidgetFactory", "size: ${widgetList.size}")
////        return fakeList.size
//        return widgetList.size
//    }
//
//    override fun getViewAt(position: Int): RemoteViews {
////        val widgetPhoto = widgetPhotoList[position]
////        val remoteView = RemoteViews(context.packageName, R.layout.item_widget)
////        remoteView.setImageViewUri(R.id.widget_photoImageView, Uri.parse(widgetPhoto.thumbUrl))
////        return remoteView
//
////        val remoteView = RemoteViews(context.packageName, R.layout.item_widget)
////        remoteView.setTextViewText(R.id.fake_text, fakeList[position].content)
//
//        val remoteView = RemoteViews(context.packageName, R.layout.item_widget)
//        val widget = widgetList[position]
////        remoteView.setImageViewUri(R.id.widget_photoImageView, Uri.parse(widget))
//
//        try {
//            val bitmap = Glide.with(context)
//                .asBitmap()
//                .load(widget)
////                .thumbnail(0.1f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                .get()
//            remoteView.setImageViewBitmap(R.id.widget_photoImageView, bitmap)
//
//        } catch (e: Exception){
//            e.printStackTrace()
//        }
//
//        return remoteView
//    }
//
//    override fun getLoadingView(): RemoteViews? {
//        return null
//    }
//
//    override fun getViewTypeCount(): Int {
//        return 1
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun hasStableIds(): Boolean {
//        return true
//    }
//}