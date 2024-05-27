package com.example.finebyme

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.databinding.ActivityPhotoDetailBinding
import com.example.finebyme.view.PhotoDetailFragment
import com.example.finebyme.viewmodel.PhotoViewModel
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.remote.model.PhotoData

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var photoViewModel: PhotoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        val view = binding.root

//        setPhotoDetailFragment()

        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]
        photoViewModel.selectedPosition.observe(this, Observer { position ->
            // position을 사용하여 필요한 작업 수행
        })
        photoViewModel.photoData.observe(this, Observer { photoList ->
            // photoList를 사용하여 필요한 작업 수행
            Log.d("PhotoDetailActivity", "Photo list size: ${photoList.size}")
            // photoViewModel.selectedPosition.value를 사용하여 선택된 포지션을 가져옵니다.
            val selectedPosition = photoViewModel.selectedPosition.value ?: 0
            // photoList에서 선택된 포지션에 해당하는 이미지 데이터에 접근합니다.
            val selectedPhoto = photoList.getOrNull(selectedPosition)
            // selectedPhoto가 null이 아닌 경우에만 작업을 수행합니다.
            selectedPhoto?.let { photo ->
                // 선택된 이미지 데이터를 사용하여 원하는 작업을 수행합니다.
                Log.d("PhotoDetailActivity", "Selected photo id: ${photo.id}")
                Log.d("PhotoDetailActivity", "Selected photo createdAt: ${photo.createdAt}")
                // 추가적으로 필요한 작업을 여기에 수행할 수 있습니다.
            }
        })

        setContentView(view)
    }

    private fun getDetailPhotos(selectedImage: PhotoData) {
        Log.d("photo_detail_id: ", selectedImage.id)
        Log.d("photo_detail_createdAt: ", selectedImage.createdAt)
        Log.d("photo_detail_altDescription: ", selectedImage.altDescription.toString())
        Log.d("photo_detail_description: ", selectedImage.description.toString())
        Log.d("photo_detail_urls: ", selectedImage.urls.regular)
        Log.d("photo_detail_color: ", selectedImage.color)
    }

    private fun setPhotoDetailFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val photoDetailFragment = PhotoDetailFragment()
        fragmentTransaction.replace(R.id.photo_detail_container, photoDetailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}