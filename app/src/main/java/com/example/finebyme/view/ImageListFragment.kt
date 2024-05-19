package com.example.finebyme.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.data.remote.repository.PhotoRepository
import com.example.finebyme.databinding.FragmentImageListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.math.log


class ImageListFragment : Fragment() {
    private lateinit var binding: FragmentImageListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        //생명주기를 인식하여 한전하게 코루틴 사용
//        lifecycleScope.launch {
//            try {
//                val phoList = withContext(Dispatchers.IO){
//                    PhotoRepository.unsplashApi.getPhotoList()
//                }
//                getPhotos(phoList)
//
//            } catch (e: Exception){
//                e.printStackTrace()
//                Toast.makeText(requireContext(), "Error fetching photos", Toast.LENGTH_SHORT).show()
//            }
//        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val phoList = withContext(Dispatchers.IO) {
                    PhotoRepository.unsplashApi.getPhotoList()
                }
                getPhotos(phoList)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Error fetching photos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotos(phoList: List<PhotoData>) {
        for (photo in phoList){
            Log.d("photo_id: ", photo.id)
            Log.d("photo_createdAt: ", photo.createdAt)
            Log.d("photo_altDescription: ", photo.altDescription)
            Log.d("photo_description: ", photo.description.toString())
            Log.d("photo_urls: ", photo.urls.regular)
            Log.d("photo_color: ", photo.color)
        }
    }
}