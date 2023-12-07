package com.example.roadtofit.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.roadtofit.databinding.ActivityWelcomeBinding
import com.example.roadtofit.ui.auth.LoginActivity
import com.example.roadtofit.ui.auth.RegisterActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        ObjectAnimator.ofFloat(binding.tagLine, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        
        val appName = ObjectAnimator.ofFloat(binding.tagLine, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.loginRegister, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val divider1 = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)


        val together = AnimatorSet().apply {
            playTogether(register, divider1)
        }

        AnimatorSet().apply {
            playSequentially(appName, title, together)
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            btnRegister.setOnClickListener {
                startActivity(Intent(this@WelcomeActivity, RegisterActivity::class.java))
            }
            btnLogin.setOnClickListener {
                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val backIntent = Intent(Intent.ACTION_MAIN)
        backIntent.addCategory(Intent.CATEGORY_HOME)
        backIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(backIntent)
    }
}