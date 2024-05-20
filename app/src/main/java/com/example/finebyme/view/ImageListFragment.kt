package com.example.finebyme.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.data.remote.repository.PhotoRepository
import com.example.finebyme.databinding.FragmentImageListBinding
import com.example.finebyme.viewmodel.PhotoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class ImageListFragment : Fragment() {
    private lateinit var binding: FragmentImageListBinding
    private lateinit var photoViewModel: PhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoViewModel = ViewModelProvider(this)[PhotoViewModel::class.java]

        //viewModel 의 photoData 관찰하여 데이터가 변경될 때마다 UI를 업데이트
        photoViewModel.photoData.observe(viewLifecycleOwner, Observer { photoList ->
            if (photoList != null) {
                getPhotos(photoList)
            } else {
                Toast.makeText(requireContext(), "No photos found", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getPhotos(phoList: List<PhotoData>) {
        for (photo in phoList) {
            Log.d("photo_id: ", photo.id)
            Log.d("photo_createdAt: ", photo.createdAt)
            Log.d("photo_altDescription: ", photo.altDescription)
            Log.d("photo_description: ", photo.description.toString())
            Log.d("photo_urls: ", photo.urls.regular)
            Log.d("photo_color: ", photo.color)
        }
    }
}