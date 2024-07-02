package com.example.finebyme.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.presentation.databinding.ItemPhotoBinding

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.Holder>() {

    //클린 아키텍쳐
    private var photos: MutableList<Photo> = arrayListOf()   //비어있는 불변 리스트 생성

    //클릭 인터페이스 정의
    interface OnPhotoItemClickListener{
        fun onPhotoClick(position: Int, photo: List<Photo>)
    }

    //클릭 리스너 선언
    // private lateinit var pItemClickListener: OnPhotoItemClickListener
    private var pItemClickListener: OnPhotoItemClickListener? = null    //외부에서 안쓸경우를 대비하기 위해 null 가능 처리

    //클릭 리스너 등록 메서드(메인 람다식 혹은 inner 클래스로 호출)
    fun setPhotoItemClickListener(itemClickListener: OnPhotoItemClickListener){
        pItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addItem(photoList: List<Photo>) {
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


    inner class Holder(private val binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                //아이템 클릭했을 때 실행되는 코드
                pItemClickListener?.onPhotoClick(adapterPosition, photos)
            }
        }

        fun bind(photo: Photo) {
            binding.apply {
                Glide.with(binding.root.context)
                    .load(photo.thumbUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.photoImageView)
            }
        }

    }

}