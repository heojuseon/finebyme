package com.example.finebyme.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.databinding.ActivityPhotoDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.viewmodel.PhotoFavoriteViewModel
import com.example.finebyme.viewmodel.PhotoFavoriteViewModelFactory

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var photoFavoriteViewModel: PhotoFavoriteViewModel

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        val view = binding.root
        setContentView(view)

        position = intent.getIntExtra("position", -1)   //추후 DB 작업 진행

        initViewModel()
        setupListener()
        setupObservers()

    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {

            if (photoFavoriteViewModel.isFavorite.value == false) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("result_position", position)
                    Log.d("result_position: ", position.toString())
                })
            }

            this.isEnabled = false
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initViewModel() {
//        val selectedImage: PhotoData? = intent.getParcelableExtra("photoList")    //deprecated
        val selectedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<PhotoData>("photoList", PhotoData::class.java)
        } else {
            intent.getParcelableExtra("photoList")
        }
        val fromFavorite = intent.getBooleanExtra("fromFavoriteImgFragment", false)
        //viewmodel 초기화
        photoFavoriteViewModel = ViewModelProvider(
            this,
            PhotoFavoriteViewModelFactory(PhotoRoomRepository(application))
        )[PhotoFavoriteViewModel::class.java]

        selectedImage?.let {
            photoFavoriteViewModel.onCreateViewModel(selectedImage, fromFavorite)
        }
    }

    private fun setupListener() {
        binding.photoLike.setOnClickListener {
            photoFavoriteViewModel.tapPhotoLike()
        }

        binding.downBtn.setOnClickListener {
            Toast.makeText(this, "downLoad", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupObservers() {
        photoFavoriteViewModel.isFavorite.observe(this, Observer {
            Log.d("isFavorite: ", it.toString())
            binding.photoLike.isChecked = it
        })

        photoFavoriteViewModel.photo.observe(this, Observer { photo ->
            photo?.let {
                Log.d("detail_data: ", photo.id)
                binding.detailTitle.text = photo.altDescription
                if (photo.description != null) {
                    binding.detailDescription.text = photo.description
                } else {
                    binding.detailDescription.text = "null"
                }
                binding.detailWidth.text = "width: ${photo.width}"
                binding.detailHeight.text = "height: ${photo.height}"

                Glide.with(this)
                    .load(photo.urls.full)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.photoDetailImageview)
            }
        })
    }
}