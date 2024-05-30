package com.example.finebyme.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.databinding.ActivityPhotoDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.viewmodel.PhotoRoomViewModel
import com.example.finebyme.viewmodel.PhotoRoomViewModelFactory

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var photoRoomViewModel: PhotoRoomViewModel

    private var likeOn: Boolean = false
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

        //viewmodel 초기화
        photoRoomViewModel = ViewModelProvider(this, PhotoRoomViewModelFactory(PhotoRoomRepository(application)))[PhotoRoomViewModel::class.java]
        initView(position, selectedImage)

    }

    private fun initView(position: Int, selectedImage: PhotoData?) {

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

        binding.photoLike.isChecked = likeOn
        binding.photoLike.setOnCheckedChangeListener { buttonView, isChecked ->
            likeOn = isChecked
            Toast.makeText(this, "Checked is: $isChecked", Toast.LENGTH_SHORT).show()
            if (isChecked){
                // 삽입할 Photo 객체 생성
                val photo = selectedImage?.let {
                    Photo(
                        id = selectedImage.id,
                        width = selectedImage.width,
                        height = selectedImage.height,
                        description = selectedImage.description,
                        altDescription = selectedImage.altDescription,
                        url = selectedImage.urls.regular
                    )
                }
                if (photo != null) {
                    photoRoomViewModel.insert(photo)
                }
            }
        }
    }

}