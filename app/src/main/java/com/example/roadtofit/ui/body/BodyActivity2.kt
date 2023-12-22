package com.example.roadtofit.ui.body

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityBody2Binding
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.profile.ProfileActivity

class BodyActivity2 : AppCompatActivity() {

    private val bodyViewModel by viewModels<BodyViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityBody2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBody2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe the bodyResponse LiveData to get the API response
        bodyViewModel.bodyResponse.observe(this, Observer { bodyResponse ->
            bodyResponse?.let {
                if (it.success) {
                    binding.tvType.text = it.result
                } else {

                }
            }
        })

        binding.btnKembali.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnUlang.setOnClickListener {
            val intent = Intent(this, BodyActivity::class.java)
            startActivity(intent)
        }

        // Trigger the API call to get body data
//        bodyViewModel.getBodyData()
    }
}
