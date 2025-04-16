package com.example.finebyme

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.finebyme.databinding.ActivityMainBinding
import com.example.finebyme.presentation.view.FavoriteImgFragment
import com.example.finebyme.presentation.view.ImageListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // SplashScreen을 적용한다.
        // setContentView 전에 작성해야 한다.
       val splashScreen = installSplashScreen()
//        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //2초 지연
        splashScreen.setKeepOnScreenCondition { true }
        Handler(Looper.getMainLooper()).postDelayed({
            splashScreen.setKeepOnScreenCondition { false }
        }, 2000)

        //test 용 -> true 가 반환될때까지 계속 splash 화면 보여짐
//        val content: View = findViewById(android.R.id.content)
//        content.viewTreeObserver.addOnPreDrawListener { false }

        setContentView(view)

        setBottomNavigationView()

        //앱 초기 실행 시 메인화면 설정
        if (savedInstanceState == null){
            binding.bottomNavView.selectedItemId = R.id.fragment_image_list
        }

        checkPermission()
    }

    private fun checkPermission() {
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED) {
            //TODO : 앱 실행시 퍼미션 등록되어있는 경우
        } else {
            //TODO : 퍼미션 등록 안되어있을 경우 launch
            requestPermissionLauncher.launch(Manifest.permission.WRITE_CONTACTS)
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

    override fun onPause() {
        super.onPause()
        Log.d("!@#!@#", "MainActivity_onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("!@#!@#", "MainActivity_onResume")
    }


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            //TODO : 퍼미션 허용
        } else {
            //TODO : 퍼미션 거부
            finish()
        }
    }
}