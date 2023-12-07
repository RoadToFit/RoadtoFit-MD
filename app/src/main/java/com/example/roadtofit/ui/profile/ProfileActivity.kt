package com.example.roadtofit.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.roadtofit.ui.profile.ProfileViewModel
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityProfileBinding
import com.example.roadtofit.ui.main.MainActivity
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

        binding.btnSave.setOnClickListener{
            // saat login data id nya disimpen di share preference, di user pref nya harus ada fungsi buat ngambil id nya di shared pref
            profileViewModel.doUpdate(binding.editEmail.text.toString(), binding.editUser.text.toString())
        }

        btnBack()

        profileViewModel.profileResponse.observe(this) {
            if (it != null) {
                it.success
                if (it.success) {
                    Toast.makeText(applicationContext, "Profile berhasil diupdate", Toast.LENGTH_SHORT).show() // ini ada titik komanya setelah kurung show
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Gagal mengupdate profil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun btnBack() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}