package com.example.roadtofit.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityMainBinding
import com.example.roadtofit.ui.WelcomeActivity
import com.example.roadtofit.ui.body.BodyActivity
import com.example.roadtofit.ui.diet.DietActivity
import com.example.roadtofit.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private var token = ""
    private val mainViewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.fabMenu)
        bottomNavigationView.selectedItemId = R.id.menu_home


        setupUser()
        setupViewModel()


        binding.homeActivity.btnEdit.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.homeActivity.btnBody.setOnClickListener {
            val intent = Intent(this, BodyActivity::class.java)
            startActivity(intent)
        }

        binding.homeActivity.btnDietFitness.setOnClickListener {
            val intent = Intent(this, DietActivity::class.java)
            startActivity(intent)
        }


            bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.menu_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.menu_classifier -> {
                    startActivity(Intent(this, BodyActivity::class.java))
                    true
                }
                R.id.menu_assistant -> {
                    startActivity(Intent(this, DietActivity::class.java))
                    true
                }
                else -> false
            }
        }


    }

    private fun setupUser() {
        mainViewModel.getSession().observe(this@MainActivity) {
            if (!it.isLogin) {
                moveActivity()
            }
            else {
                val userName = it.name
                binding.homeActivity.tvUser.text = userName

            }
        }

        showToast()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }


    private fun moveActivity() {
        startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
        finish()
    }

    private fun showToast() {
        mainViewModel.toastText.observe(this@MainActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@MainActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        val backIntent = Intent(Intent.ACTION_MAIN)
//        backIntent.addCategory(Intent.CATEGORY_HOME)
//        backIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(backIntent)
//    }

}