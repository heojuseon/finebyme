package com.example.finebyme.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.presentation.databinding.ActivityPhotoDetailBinding
import com.example.finebyme.viewmodel.PhotoFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
    //PhotoFavoriteViewModel 클래스에서 @HiltViewModel 를 사용하여  viewmodel 초기화 작업 따로 안해도됨
    private val photoFavoriteViewModel: PhotoFavoriteViewModel by viewModels()

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

        selectedImage?.let {
            photoFavoriteViewModel.onCreateViewModel(selectedImage, fromFavorite)
        }
    }

    private fun setupListener() {
        binding.photoLike.setOnClickListener {
            photoFavoriteViewModel.tapPhotoLike()
        }

        //url_downLoad
        binding.downBtn.setOnClickListener {
            Toast.makeText(this, "downLoad", Toast.LENGTH_SHORT).show()
//            photoFavoriteViewModel.downloadImage()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {    //TIRAMISU 이상의 버전
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {  //권한 있을 경우 : PERMISSION_GRANTED, 권한 없을 경우 : PERMISSION_DENIED
                    //권한 설정 되어있을 경우
                    downloadImage()
                } else {
                    //권한 물어보기
                    //여러개의 원한을 요청 할 경우가 있을 수 있어 인자로 array 를 받는다.
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        REQUEST_CODE_READ_MEDIA_IMAGES
                    )
                }
            } else {    //TIRAMISU 이하의 버전
                downloadImage()
            }
        }
    }

    private fun downloadImage() {
        photoFavoriteViewModel.downloadImage(applicationContext)
        //ViewModel에서 Context를 직접 참조하는 것은 메모리 누수를 초래할 수 있으므로 권장되지 않음
//        photoFavoriteViewModel.downloadImage()
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


    //onRequestPermissionsResult : 권한팝업이 뜨고 권한을 허용을 하였는지 거부 하였는지를 체크하여 해당 이벤트에 맞는 동작을 실행하는 함수
    //override 하여 사용
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_MEDIA_IMAGES) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) { //권한 허용을 클릭 했을 때 로직
                //권한이 허용 되었을 경우 PackageManager.PERMISSION_GRANTED 으로 넘어옴으로 권한 승인 후 동작을 구현
                downloadImage()
            } else {    //권한 거절을 클릭 했을 때 로직
                //권한이 처음으로 거절되었을 경우는 반환값으로 true가 반환되고, 두번째는 flase가 반환
                //ex) 한번더 권한을 요청하게끔 구현 가능
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_READ_MEDIA_IMAGES = 100
    }
}