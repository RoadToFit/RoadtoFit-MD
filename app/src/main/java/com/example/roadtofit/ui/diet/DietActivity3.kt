package com.example.roadtofit.ui.diet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.sofitapp.ui.result.DietViewModel
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityDiet3Binding

class DietActivity3 : AppCompatActivity() {

    private val dietViewModel: DietViewModel by viewModels { factory }
    private lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityDiet3Binding
    private lateinit var btnPertahankan: Button
    private lateinit var btnRingan: Button
    private lateinit var btnSedang: Button
    private lateinit var btnEkstrem: Button
    private var input = ""

    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()

        binding = ActivityDiet3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRingan = binding.btnRingan
        btnSedang = binding.btnSedang
        btnPertahankan = binding.btnPertahankan
        btnEkstrem = binding.btnEsktrem

        btnRingan.setOnClickListener {
            selectButton(it as Button, "ringan")
        }

        btnSedang.setOnClickListener {
            selectButton(it as Button, "moderat")
        }

        btnPertahankan.setOnClickListener {
            selectButton(it as Button, "ringan")
        }

        btnEkstrem.setOnClickListener {
            selectButton(it as Button, "berat")
        }

        binding.btnNext.setOnClickListener {
            // Call the doPredict function to send the selected input to the API
            dietViewModel.doPredict(input)
            binding.btnNext.setOnClickListener {
                val intent = Intent(this, DietActivity2::class.java)
                intent.putExtra("selectedInput", input)
                startActivity(intent)
            }
        }


        binding.btnUlang.setOnClickListener {
            val intent = Intent(this, DietActivity2::class.java)
            startActivity(intent)
        }

        // Observe the dietResponse LiveData for any changes
        dietViewModel.dietResponse.observe(this, Observer {
            // Handle the response here if needed
            // For example, you can navigate to the next activity based on the response
            val intent = Intent(this, DietActivity2::class.java)
            startActivity(intent)
        })
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun selectButton(clickedButton: Button, selectedInput: String) {
        selectedButton?.apply {
            setBackgroundColor(resources.getColor(R.color.seashell))
            setTextColor(resources.getColor(R.color.black))
        }

        clickedButton.apply {
            setBackgroundColor(resources.getColor(R.color.venice_blue))
            setTextColor(resources.getColor(R.color.white))
        }

        selectedButton = clickedButton
        input = selectedInput
    }
}
