package com.example.finebyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.finebyme.databinding.ActivityPhotoDetailBinding
import com.example.finebyme.view.PhotoDetailFragment
import com.example.finebyme.viewmodel.PhotoViewModel
import androidx.lifecycle.Observer
import com.example.finebyme.data.remote.model.PhotoData

class PhotoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoDetailBinding
//    private lateinit var viewModel: PhotoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        val view = binding.root

        setPhotoDetailFragment()

        setContentView(view)
    }

    private fun setPhotoDetailFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val photoDetailFragment = PhotoDetailFragment()
        fragmentTransaction.replace(R.id.photo_detail_container, photoDetailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}