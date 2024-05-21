package com.example.finebyme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.databinding.ItemPhotoBinding

class PhotoAdapter: RecyclerView.Adapter<PhotoAdapter.Holder>() {
    private lateinit var binding: ItemPhotoBinding
    private var photos: List<PhotoData> = emptyList()   //비어있는 불변 리스트 생성

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoAdapter.Holder {
        binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: PhotoAdapter.Holder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addItem(photoList: List<PhotoData>) {
        photos = photoList
        notifyDataSetChanged()
    }


    inner class Holder(binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: PhotoData) {
            binding.apply {
                Glide.with(binding.root)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.photoImageView)
            }
        }

    }

}