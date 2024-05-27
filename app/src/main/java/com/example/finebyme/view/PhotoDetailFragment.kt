package com.example.finebyme.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.viewmodel.PhotoViewModel
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.databinding.FragmentPhotoDetailBinding

class PhotoDetailFragment : Fragment() {
    private lateinit var binding: FragmentPhotoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDetail()
    }

    private fun initDetail() {
        //ViewModel 생성 시 Owner 파라미터에 this 대신 requireActivity()를 사용해야 한다.
        //this를 사용한다면 두 프레그먼트는 서로다른 Owner를 갖게 된다.
        val photoViewModel = ViewModelProvider(requireActivity())[PhotoViewModel::class.java]
        photoViewModel.photoData.observe(viewLifecycleOwner, Observer{ photoList ->
            photoViewModel.selectedPosition.observe(viewLifecycleOwner, Observer{position ->
                if (photoList != null){
                    val selectedImage = photoList[position]
                    getDetailPhotos(selectedImage)
                }
            })
        })
    }

    private fun getDetailPhotos(selectedImage: PhotoData) {
        Log.d("photo_detail_id: ", selectedImage.id)
        Log.d("photo_detail_createdAt: ", selectedImage.createdAt)
        Log.d("photo_detail_altDescription: ", selectedImage.altDescription.toString())
        Log.d("photo_detail_description: ", selectedImage.description.toString())
        Log.d("photo_detail_urls: ", selectedImage.urls.regular)
        Log.d("photo_detail_color: ", selectedImage.color)

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