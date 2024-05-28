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

        initView()

        setContentView(view)
    }

    private fun initView() {
        val position = intent.getIntExtra("position", -1)   //추후 DB 작업 진행
//        val selectedImage: PhotoData? = intent.getParcelableExtra("photoList")    //deprecated
        val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<PhotoData>("photoList", PhotoData::class.java)
        } else {
            intent.getParcelableExtra("photoList")
        }

        if (selectedImage != null) {
            Log.d("detail_data: ", selectedImage.id)
            binding.detailTitle.text = selectedImage.altDescription
            if (selectedImage.description != null){
                binding.detailDescription.text = selectedImage.description
            }else{
                binding.detailDescription.text = "null"
            }
            binding.detailWidth.text = "width: ${selectedImage.width}"
            binding.detailHeight.text = "height: ${selectedImage.height}"

            Glide.with(this)
                .load(selectedImage.urls.full)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.photoDetailImageview)
        }
    }
}