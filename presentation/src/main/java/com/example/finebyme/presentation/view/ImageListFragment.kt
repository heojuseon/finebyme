package com.example.finebyme.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finebyme.presentation.adapter.PhotoAdapter
import com.example.finebyme.presentation.databinding.FragmentImageListBinding
import com.example.finebyme.presentation.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListFragment : Fragment() {
    private lateinit var binding: FragmentImageListBinding
    //@HiltViewModel 를 사용하여  viewmodel 초기화 작업 따로 안해도됨
    //activityViewModels: ViewModel을 Activity 범위로 설정하여 Fragment가 재생성되더라도 동일한 ViewModel 인스턴스를 사용할 수 있도록 해야한다.
    private val photoViewModel: PhotoViewModel by activityViewModels()
    private val adapter = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater)
        Log.d("!!!!!!!", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("!!!!!!!", "onViewCreated")


        /*
        this -> this 를 사용하게 될경우 fragment 가 재생성(호출)될 때마다 viewmodel 도 새로 생성되면서 리스트가 화면에 다시 뿌려지는 현상이 발생
                (해당 Fragment가 다시 그려질 때마다 ViewModel이 초기화되므로, 리스트를 새로 호출)
         */
        /*
        requireActivity -> ViewModel을 Activity 범위로 설정하여 Fragment가 재생성되더라도 동일한 ViewModel 인스턴스를 사용할 수 있도록 해야한다.
                            (ViewModel은 Activity 범위 내에서만 존재하게 되므로 Fragment가 재생성되더라도 동일한 ViewModel 인스턴스를 사용)
         */
//        photoViewModel = ViewModelProvider(requireActivity())[PhotoViewModel::class.java]

        editListener()
        initPhoto()
    }

    private fun editListener() {

        binding.searchImg.setText(photoViewModel.query.value.toString())
        binding.searchImg.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //텍스트가 바뀌기 전 이벤트
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //텍스트가 바뀌었을 때 이벤트
                val query = s.toString()
                Log.d("!!!!!!!", "onTextChanged: $query")
                photoViewModel.searchImg(query)
            }
            override fun afterTextChanged(s: Editable?) {
                //텍스트가 바뀌고 난 뒤 이벤트
            }
        })

    }

    private fun initPhoto() {

        adapter.setPhotoItemClickListener(object : OnPhotoItemClickListener{
            override fun onPhotoClick(position: Int, photoList: List<PhotoData>) {
                Toast.makeText(
                    context,
                    "photoId: ${photoList[position].id} + position: $position",
                    Toast.LENGTH_SHORT
                ).show()

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

    private fun getPhotos(photoList: List<PhotoData>) {
        for (photo in photoList) {
            Log.d("photo_id: ", photo.id)
            Log.d("photo_altDescription: ", photo.altDescription.toString())
            Log.d("photo_description: ", photo.description.toString())
            Log.d("photo_urls: ", photo.urls.regular)
        }
    }
}