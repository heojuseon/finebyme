package com.example.finebyme.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.databinding.ActivityPhotoDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.viewmodel.PhotoFavoriteViewModel
import com.example.finebyme.viewmodel.PhotoFavoriteViewModelFactory
import com.example.finebyme.viewmodel.PhotoRoomViewModel
import com.example.finebyme.viewmodel.PhotoRoomViewModelFactory

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var photoFavoriteViewModel: PhotoFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val position = intent.getIntExtra("position", -1)   //추후 DB 작업 진행
//        val selectedImage: PhotoData? = intent.getParcelableExtra("photoList")    //deprecated
        val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<PhotoData>("photoList", PhotoData::class.java)
        } else {
            intent.getParcelableExtra("photoList")
        }
       val isFavorite = intent.getBooleanExtra("fromFavoriteImgFragment", false)

        //viewmodel 초기화
        photoFavoriteViewModel = ViewModelProvider(
            this,
            PhotoFavoriteViewModelFactory(PhotoRoomRepository(application))
        )[PhotoFavoriteViewModel::class.java]

        selectedImage?.let {
            photoFavoriteViewModel.onCreateViewModel(selectedImage, isFavorite)
        }


        photoFavoriteViewModel.isFavorite.observe(this, Observer {
            Log.d("isFavorite: ", it.toString())
            initView(position, selectedImage, it)
        })

    }

    private fun initView(position: Int, selectedImage: PhotoData?, isFavorite: Boolean) {

        if (selectedImage != null) {
            Log.d("detail_data: ", selectedImage.id)
            binding.detailTitle.text = selectedImage.altDescription
            if (selectedImage.description != null) {
                binding.detailDescription.text = selectedImage.description
            } else {
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

        binding.photoLike.isChecked = isFavorite
        binding.photoLike.setOnCheckedChangeListener { buttonView, isChecked ->
            if (selectedImage != null) {
                photoFavoriteViewModel.isFavoriteCheck(selectedImage, isChecked)
            }
        }
    }

}