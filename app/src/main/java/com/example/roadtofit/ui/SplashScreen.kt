package com.example.roadtofit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivitySplashScreenBinding
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.main.MainViewModel

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }
    private val duration = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            mainViewModel.getSession().observe(this@SplashScreen) {
                if (it.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                }
                finish()
            }
        },duration)
    }
}