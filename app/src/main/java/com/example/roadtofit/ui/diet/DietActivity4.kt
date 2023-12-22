package com.example.roadtofit.ui.diet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.roadtofit.R
import com.example.roadtofit.databinding.ActivityDiet4Binding
import com.example.roadtofit.ui.main.MainActivity

class DietActivity4 : AppCompatActivity() {

    private lateinit var binding: ActivityDiet4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiet4Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val bmiResult = intent.getDoubleExtra("BMI_RESULT", 0.0)

        Log.d("DietActivity4", "BMI Result: $bmiResult")

        binding.tvResult.text = String.format(getString(R.string.bmi_result), bmiResult)

        binding.btnGenerate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnUlang.setOnClickListener {
            val intent = Intent(this, DietActivity3::class.java)
            startActivity(intent)
        }
    }
}


