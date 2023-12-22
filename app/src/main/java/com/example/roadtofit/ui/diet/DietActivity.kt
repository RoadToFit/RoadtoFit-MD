package com.example.roadtofit.ui.diet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.roadtofit.R
import com.example.roadtofit.databinding.ActivityDietBinding
import com.example.roadtofit.ui.body.BodyActivity
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class DietActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDietBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.fabMenu)
        bottomNavigationView.selectedItemId = R.id.menu_assistant

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
                    // Use the current activity
                    true
                }
                else -> false
            }
        }

        val etAge: EditText = binding.etAge
        val etWeight: EditText = binding.etWeight
        val etHeight: EditText = binding.etHeight
        val btnNext: Button = binding.btnNext

        btnNext.setOnClickListener {

            val age = etAge.text.toString().toDoubleOrNull()
            val weight = etWeight.text.toString().toDoubleOrNull()
            val height = etHeight.text.toString().toDoubleOrNull()

            if (weight != null && height != null && height > 0) {
                val bmi = calculateBMI(weight, height)

                val intent = Intent(this, DietActivity3::class.java)
                intent.putExtra("BMI_RESULT", bmi)
                intent.putExtra("WEIGHT", weight.toInt())
                intent.putExtra("HEIGHT", height.toInt())
                intent.putExtra("AGE", age)  // Assuming 'age' is a variable in your code
                startActivity(intent)
            } else {
                // Handle invalid input
            }
        }


        btnBack()
    }


    private fun btnBack() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun calculateBMI(weight: Double, height: Double): Double {
        Log.d("DietActivity", "Calculating BMI...")

        // BMI formula: BMI = weight(kg) / (height(m) * height(m))
        val doubleHeight = height / 100 // Convert height to meters if it's in centimeters

        val heigh = doubleHeight * doubleHeight
        val bmi = weight / heigh

        Log.d("DietActivity", "Weight: $doubleHeight kg")
        Log.d("DietActivity", "Height: $height cm")
        Log.d("DietActivity", "BMI: $bmi")

        return bmi
    }

}
