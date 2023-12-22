package com.example.roadtofit.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityProfileBinding
import com.example.roadtofit.ui.WelcomeActivity
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.main.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException

class ProfileActivity : AppCompatActivity() {

    private var imageUri: Uri? = null
    private lateinit var binding: ActivityProfileBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }


    private val openGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            uploadImage()
        } else {
            Log.d("Photo", "No photo selected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.btnUpload.setOnClickListener {
            intentGallery()
        }

        binding.btnSave.setOnClickListener{
            // saat login data id nya disimpen di share preference, di user pref nya harus ada fungsi buat ngambil id nya di shared pref
            profileViewModel.doUpdate(binding.etName.text.toString())
        }

//        btnBack()
        btnLogout()

        profileViewModel.profileResponse.observe(this) {
            if (it != null) {
                it.success
                if (it.success) {
                    Toast.makeText(applicationContext, "Profile berhasil diupdate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Gagal mengupdate profil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun intentGallery(){
        openGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun uploadImage() {
        imageUri?.let { uri ->
            val imageByteArray = getImageByteArrayFromUri(uri)
            if (imageByteArray != null) {
                profileViewModel.uploadProfile(imageByteArray)
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

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

//    private fun btnBack() {
//        binding.ivBack.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            finish()
//        }
//    }

    private fun btnLogout() {
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
            profileViewModel.logout()
        }
    }


}