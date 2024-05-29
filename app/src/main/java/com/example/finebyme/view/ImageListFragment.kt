package com.example.finebyme.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finebyme.R
import com.example.finebyme.adapter.PhotoAdapter
import com.example.finebyme.adapter.PhotoAdapter.OnPhotoItemClickListener
import com.example.finebyme.data.db.entity.PhotoData
import com.example.finebyme.databinding.FragmentImageListBinding
import com.example.finebyme.viewmodel.PhotoViewModel


class ImageListFragment : Fragment() {
    private lateinit var binding: FragmentImageListBinding
    private lateinit var photoViewModel: PhotoViewModel
    private val adapter = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPhoto()

    }

    private fun initPhoto() {

        adapter.setPhotoItemClickListener(object : OnPhotoItemClickListener{
            override fun onPhotoClick(position: Int, photoList: List<PhotoData>) {
                Toast.makeText(context, "photoId: ${photoList[position].id} + position: $position", Toast.LENGTH_SHORT).show()

//                //Fragment to Fragment
//                //ViewModel을 통해 클릭된 이미지 리스트와 포지션 값 설정
//                photoViewModel.sendData(position, photoList)
//                moveDetailFragment()

                //Fragment to Activity
                val intent = Intent(context, PhotoDetailActivity::class.java)
                val selectedImage = photoList[position]
                intent.putExtra("position", position)
                intent.putExtra("photoList", selectedImage)
                startActivity(intent)   // 추후 registerForActivityResult() 사용 생각
            }
        })

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        photoViewModel = ViewModelProvider(requireActivity())[PhotoViewModel::class.java]
        //viewModel 의 photoData 관찰하여 데이터가 변경될 때마다 UI를 업데이트
        photoViewModel.photoData.observe(viewLifecycleOwner, Observer { photoList ->
            if (photoList != null) {
                getPhotos(photoList)
                adapter.addItem(photoList)
            } else {
                Toast.makeText(requireContext(), "No photos found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Fragment to Fragment
    private fun moveDetailFragment() {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        val detailFragment = DetailFragment()
        fragmentTransaction.replace(R.id.main_container, detailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun getPhotos(photoList: List<PhotoData>) {
        for (photo in photoList) {
            Log.d("photo_id: ", photo.id)
            Log.d("photo_altDescription: ", photo.altDescription.toString())
            Log.d("photo_description: ", photo.description.toString())
            Log.d("photo_urls: ", photo.urls.regular)
        }
    }
}