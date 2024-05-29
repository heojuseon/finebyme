package com.example.finebyme.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finebyme.R
import com.example.finebyme.adapter.PhotoAdapter
import com.example.finebyme.data.db.entity.Photo
import com.example.finebyme.data.db.repository.PhotoRoomRepository
import com.example.finebyme.databinding.FragmentFavoriteImgBinding
import com.example.finebyme.viewmodel.PhotoRoomViewModel
import com.example.finebyme.viewmodel.PhotoRoomViewModelFactory

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
        binding.favoriteRecyclerview.adapter = adapter
        binding.favoriteRecyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        photoRoomViewModel.getAll().observe(requireActivity(), Observer { favoriteList ->
            if (favoriteList != null){
                getFavorite(favoriteList)
//                adapter.addFavItem(favoriteList)
            }
        })
    }

    private fun getFavorite(favoriteList: List<Photo>) {
        for (favorite in favoriteList){
            Log.d("favorite_id: ", favorite.id)
            Log.d("favorite_width: ", favorite.width.toString())
            Log.d("favorite_height: ", favorite.height.toString())
            Log.d("favorite_description: ", favorite.description.toString())
            Log.d("favorite_altDescription: ", favorite.altDescription.toString())
            Log.d("favorite_url: ", favorite.url)
        }
    }
}