package com.example.roadtofit.ui.body

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.telecom.Call
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityBodyBinding
import com.example.roadtofit.ui.diet.DietActivity
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.IOException


class BodyActivity : AppCompatActivity() {


    private var imageUri: Uri? = null
    private lateinit var binding: ActivityBodyBinding
    private lateinit var factory: ViewModelFactory
    private val bodyViewModel: BodyViewModel by viewModels { factory }


    private val openGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            uploadImage()
            moveActivity()
        } else {
            Log.d("Photo", "No photo selected")
        }
    }



    private fun openKamera() {
        // Ensure imageUri is initialized before launching the camera
        if (imageUri == null) {
            Log.e("Photo", "ImageUri is null. Initializing a new Uri.")
            imageUri = createImageUri()
        }

        imageUri?.let {
            openKamera.launch(it)
        } ?: Log.e("Photo", "ImageUri is still null. Camera cannot be launched.")
    }

    private fun createImageUri(): Uri {
        // Create a file Uri for storing the captured image
        val fileName = "your_image_file_name.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }
        val contentResolver = contentResolver
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw IOException("Failed to create ImageUri")
    }

    private val openKamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result: Boolean? ->
        result?.let {
            if (it) {
                imageUri?.let { uri ->
                    uploadImage()
                    moveActivity()
                } ?: Log.e("Photo", "ImageUri is null")
            } else {
                Log.d("Photo", "Failed to capture a picture")
            }
        } ?: Log.e("Photo", "Result is null")
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.fabMenu)
        bottomNavigationView.selectedItemId = R.id.menu_classifier

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

        setupViewModel()

        binding.btnUpload.setOnClickListener {
            intentGallery()
        }

        binding.btnSelfie.setOnClickListener {
            openKamera()
        }

        btnBack()
        showLoading()

    }

    private fun moveActivity() {
        bodyViewModel.bodyResponse.observe(this@BodyActivity) { response ->
            response?.let {
                val message = it.message

                if (!message.isNullOrBlank()) {
                    // Registration was successful, move to BodyActivity2
                    startActivity(Intent(this@BodyActivity, BodyActivity2::class.java))
                    finish()
                } else {
                    // Handle the case where the message is blank or null
                    Log.e("MoveActivity", "Empty or null message in response")
                }
            }
        }
    }



    private fun showLoading() {
        bodyViewModel.isLoading.observe(this@BodyActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
    private fun btnBack() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun intentGallery(){
        openGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            val imageByteArray = getImageByteArrayFromUri(uri)
            if (imageByteArray != null) {
                bodyViewModel.uploadImage(imageByteArray)
            } else {
                Log.e("UploadImage", "Failed to get image data from Uri")
            }
        } ?: run {
            Log.e("UploadImage", "Image Uri is null")
        }
    }

    private fun getImageByteArrayFromUri(uri: Uri): ByteArray? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.readBytes()
        } catch (e: IOException) {
            Log.e("UploadImage", "Error reading image data", e)
            null
        }
    }




}
