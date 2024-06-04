package com.example.finebyme.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finebyme.R
import com.example.finebyme.adapter.PhotoAdapter
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.data.remote.model.PhotoData
import com.example.finebyme.databinding.FragmentFavoriteImgBinding
import com.example.finebyme.viewmodel.PhotoRoomViewModel
import com.example.finebyme.viewmodel.PhotoRoomViewModelFactory
import kotlinx.coroutines.Dispatchers.Main

class FavoriteImgFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteImgBinding
    private lateinit var photoRoomViewModel: PhotoRoomViewModel
    private val adapter = PhotoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteImgBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewmodel 초기화
        photoRoomViewModel = ViewModelProvider(this, PhotoRoomViewModelFactory(PhotoRoomRepository(requireActivity().application)))[PhotoRoomViewModel::class.java]
        initFavoritePhoto()
    }

    private fun initFavoritePhoto() {
        adapter.setPhotoItemClickListener(object : PhotoAdapter.OnPhotoItemClickListener {
            override fun onPhotoClick(position: Int, photoList: List<PhotoData>) {
                Toast.makeText(context, "photoId: ${photoList[position].id} + position: $position", Toast.LENGTH_SHORT).show()
                //Fragment to Activity
                val intent = Intent(context, PhotoDetailActivity::class.java)
                val selectedImage = photoList[position]
                intent.putExtra("position", position)
                intent.putExtra("photoList", selectedImage)
                intent.putExtra("fromFavoriteImgFragment", true)
//                startActivity(intent)   // 추후 registerForActivityResult() 사용 생각
                favoritePositionLauncher.launch(intent)
            }
        })
        binding.favoriteRecyclerview.adapter = adapter
        binding.favoriteRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        photoRoomViewModel.photoData.observe(requireActivity(), Observer { favoriteList ->
            if (favoriteList != null){
                getFavorite(favoriteList)
                adapter.addItem(favoriteList)
            }
        })
    }

    private fun getFavorite(favoriteList: List<PhotoData>) {
        for (favorite in favoriteList){
            Log.d("favorite_id: ", favorite.id)
            Log.d("favorite_width: ", favorite.width.toString())
            Log.d("favorite_height: ", favorite.height.toString())
            Log.d("favorite_description: ", favorite.description.toString())
            Log.d("favorite_altDescription: ", favorite.altDescription.toString())
            Log.d("favorite_url: ", favorite.urls.regular)
        }
    }

    val favoritePositionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("@!@", "result.resultCode : ${result.resultCode}")
        if (result.resultCode == RESULT_OK){
            val resultPosition = result.data?.getIntExtra("result_position", -1)
            Log.d("Favorite_result: ", resultPosition.toString())
            if (resultPosition != null) {
                adapter.removeItem(resultPosition)
            }
        }
    }
}