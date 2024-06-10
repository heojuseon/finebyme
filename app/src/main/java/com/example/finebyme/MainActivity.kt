package com.example.finebyme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.finebyme.databinding.ActivityMainBinding
import com.example.finebyme.view.FavoriteImgFragment
import com.example.finebyme.view.ImageListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // SplashScreen을 적용한다.
        // setContentView 전에 작성해야 한다.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setBottomNavigationView()

        //앱 초기 실행 시 메인화면 설정
        if (savedInstanceState == null){
            binding.bottomNavView.selectedItemId = R.id.fragment_image_list
        }

    }

    private fun setBottomNavigationView() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_image_list -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, ImageListFragment()).commit()
                    true
                }
                R.id.fragment_favorite_img -> {
                    supportFragmentManager.beginTransaction().replace(R.id.main_container, FavoriteImgFragment()).commit()
                    true
                }

                else -> {
                    false
                }
            }
        }
    }
}