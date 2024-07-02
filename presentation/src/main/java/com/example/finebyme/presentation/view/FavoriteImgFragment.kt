package com.example.finebyme.presentation.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finebyme.domain.entity.Photo
import com.example.finebyme.presentation.adapter.PhotoAdapter
import com.example.finebyme.presentation.databinding.FragmentFavoriteImgBinding
import com.example.finebyme.presentation.viewmodel.PhotoRoomViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteImgFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteImgBinding
    //@HiltViewModel 를 사용하여  viewmodel 초기화 작업 따로 안해도됨
    private val photoRoomViewModel: PhotoRoomViewModel by viewModels()
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

        initFavoritePhoto()
    }

    private fun initFavoritePhoto() {
        adapter.setPhotoItemClickListener(object : PhotoAdapter.OnPhotoItemClickListener {
            override fun onPhotoClick(position: Int, photo: List<Photo>) {
                Toast.makeText(
                    context,
                    "photoId: ${photo[position].id} + position: $position",
                    Toast.LENGTH_SHORT
                ).show()
                //Fragment to Activity
                val intent = Intent(context, PhotoDetailActivity::class.java)
                val selectedImage = photo[position]

                //객체를 직렬화 하지 않고 json 으로 변환후 string 형태로 전달
                val photoJson = Gson().toJson(selectedImage)
                Log.d("!@!@", "gson: $photoJson")

                intent.putExtra("position", position)
                intent.putExtra("photo", photoJson)
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

    private fun getFavorite(favoriteList: List<Photo>) {
        for (favorite in favoriteList){
            Log.d("favorite_id: ", favorite.id)
            Log.d("favorite_width: ", favorite.width.toString())
            Log.d("favorite_height: ", favorite.height.toString())
            Log.d("favorite_description: ", favorite.description)
            Log.d("favorite_altDescription: ", favorite.altDescription)
            Log.d("favorite_url: ", favorite.thumbUrl)
        }
    }

    val favoritePositionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("@!@", "result.resultCode : ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK){
            val resultPosition = result.data?.getIntExtra("result_position", -1)
            Log.d("Favorite_result: ", resultPosition.toString())
            if (resultPosition != null) {
                adapter.removeItem(resultPosition)
            }
        }
    }
}