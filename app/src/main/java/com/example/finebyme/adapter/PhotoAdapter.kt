package com.example.finebyme.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.data.remote.model.Urls
import com.example.finebyme.databinding.ItemPhotoBinding

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.Holder>() {
//    private var photos: List<PhotoData> = emptyList()   //비어있는 불변 리스트 생성
    private var photos: MutableList<PhotoData> = arrayListOf()   //비어있는 불변 리스트 생성

    //클릭 인터페이스 정의
    interface OnPhotoItemClickListener{
        fun onPhotoClick(position: Int, photoList: List<PhotoData>)
    }

    //클릭 리스너 선언
    // private lateinit var pItemClickListener: OnPhotoItemClickListener
    private var pItemClickListener: OnPhotoItemClickListener? = null    //외부에서 안쓸경우를 대비하기 위해 null 가능 처리

    //클릭 리스너 등록 메서드(메인 람다식 혹은 inner 클래스로 호출)
    fun setPhotoItemClickListener(itemClickListener: OnPhotoItemClickListener){
        pItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.Holder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: PhotoAdapter.Holder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addItem(photoList: List<PhotoData>) {
//        photos = photoList
        photos = photoList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(resultPosition: Int) {
        Log.d("@!@", "removeItem 1 : ${photos.size}")
        photos.removeAt(resultPosition)
        notifyItemRemoved(resultPosition)
        Log.d("@!@", "removeItem 2 : ${photos.size}")
    }


    //mapping 작업
//    fun addFavItem(favoriteList: List<Photo>) {
//        val convert = favoriteList.map {
//            PhotoData(
//                id = it.id,
//                width = it.width,
//                height = it.height,
//                description = it.description,
//                altDescription = it.altDescription,
//                urls = Urls(regular = it.url, full = it.url)
//            )
//        }
//        addItem(convert)
//    }


    inner class Holder(private val binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                //아이템 클릭했을 때 실행되는 코드
                pItemClickListener?.onPhotoClick(adapterPosition, photos)
            }
        }

        fun bind(photo: PhotoData) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.photoImageView)
            }
        }

    }

}