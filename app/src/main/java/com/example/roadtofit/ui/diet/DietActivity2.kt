package com.example.roadtofit.ui.diet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.capstone.sofitapp.ui.result.DietViewModel
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory

import com.example.roadtofit.databinding.ActivityDiet2Binding



class DietActivity2 : AppCompatActivity() {

    private val dietViewModel: DietViewModel by viewModels { factory }
    private lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityDiet2Binding
    private lateinit var btnRingan: Button
    private lateinit var btnSedang: Button
    private lateinit var btnAktif: Button
    private lateinit var btnBerat: Button
    private lateinit var btnSedikit: Button
    private lateinit var btnEcto: Button
    private lateinit var btnEndo: Button
    private lateinit var btnMeso: Button
    private var gender = "male"
    private var activity = ""
    private var plan = "maintain weight"
    private var numMeals = 0
    private var bodyType = ""
    private var selectedInput: String? = null


    private var selectedButton: Button? = null
    private var selectedsButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()

        binding = ActivityDiet2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val weight = intent.getIntExtra("WEIGHT", 65)
        val height = intent.getIntExtra("HEIGHT", 170)
        val age = intent.getIntExtra("AGE", 19)

        // Use binding to get references to your buttons
        btnRingan = binding.btnRingan
        btnSedang = binding.btnSedang
        btnAktif = binding.btnAktif
        btnBerat = binding.btnBerat
        btnSedikit = binding.btnSedikit
        btnEcto = binding.btnEcto
        btnMeso = binding.btnMeso
        btnEndo = binding.btnEndo

        selectedInput = intent.getStringExtra("selectedInput")


        binding.cbQuestion1.setOnCheckedChangeListener { _, isChecked ->
            numMeals = if (isChecked) {
                3
            } else {
                0
            }
        }

        binding.cbQuestion2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                numMeals = 4
            } else if (binding.cbQuestion1.isChecked) {
                numMeals = 3
            } else {
                numMeals = 0
            }
        }

        binding.cbQuestion3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                numMeals = 5
            } else if (binding.cbQuestion2.isChecked) {
                numMeals = 4
            } else if (binding.cbQuestion1.isChecked) {
                numMeals = 3
            } else {
                numMeals = 0
            }
        }

        btnSedikit.setOnClickListener {
            selectButton(it as Button, "sedentary")
        }

        btnRingan.setOnClickListener {
            selectButton(it as Button, "lightly active")
        }

        btnSedang.setOnClickListener {
            selectButton(it as Button, "moderately active")
        }

        btnAktif.setOnClickListener {
            selectButton(it as Button, "very active")
        }

        btnBerat.setOnClickListener {
            selectButton(it as Button, "extra active")
        }

        btnEcto.setOnClickListener {
            selectsButton(it as Button, "ectomorph")
        }

        btnEndo.setOnClickListener {
            selectsButton(it as Button, "endomorph")
        }

        btnMeso.setOnClickListener {
            selectsButton(it as Button, "mesomorph")
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, DietActivity4::class.java)
            startActivity(intent)
            dietViewModel.doFood(gender, height, weight, age, activity, plan, numMeals, bodyType)
        }

        binding.btnUlang.setOnClickListener {
            val intent = Intent(this, DietActivity3::class.java)
            startActivity(intent)
        }

        dietViewModel.foodResponse.observe(this, Observer {
            // Handle the response here if needed
            // For example, you can navigate to the next activity based on the response
            val intent = Intent(this, DietActivity4::class.java)
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
        activity = selectedInput

        }
    private fun selectsButton(clicksButton: Button, selectedsInput: String) {
        selectedsButton?.apply {
            setBackgroundColor(resources.getColor(R.color.seashell))
            setTextColor(resources.getColor(R.color.black))
        }

        clicksButton.apply {
            setBackgroundColor(resources.getColor(R.color.venice_blue))
            setTextColor(resources.getColor(R.color.white))
        }

        selectedsButton = clicksButton
        bodyType = selectedsInput

    }

    }

