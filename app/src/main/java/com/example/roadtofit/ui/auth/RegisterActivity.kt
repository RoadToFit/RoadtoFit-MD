package com.example.roadtofit.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.roadtofit.R
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityRegisterBinding
import com.example.roadtofit.ui.WelcomeActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()
        setupViewModel()
        btnLogin()
        btnBack()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.tvTitleRegister, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val back = ObjectAnimator.ofFloat(binding.ivBack, View.ALPHA, 1f).setDuration(400)
        val name = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val editName = ObjectAnimator.ofFloat(binding.editName, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val editEmail = ObjectAnimator.ofFloat(binding.editEmail, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val editPassword = ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.regisButton, View.ALPHA, 1f).setDuration(500)
        val login1 = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val login2 = ObjectAnimator.ofFloat(binding.tvLogin2, View.ALPHA, 1f).setDuration(500)
        val gend = ObjectAnimator.ofFloat(binding.gender, View.ALPHA, 1f).setDuration(500)
        val male = ObjectAnimator.ofFloat(binding.male, View.ALPHA, 1f).setDuration(500)
        val female = ObjectAnimator.ofFloat(binding.female, View.ALPHA, 1f).setDuration(500)


        val together = AnimatorSet().apply {
            playTogether(name, editName, email, editEmail, password, editPassword, register, login1, login2, gend, male, female)
        }

        AnimatorSet().apply {
            playSequentially(back,together)
            startDelay = 400
        }.start()
    }

    private fun setupAction() {
        binding.apply {
            if (editName.length() == 0 && editEmail.length() == 0 && editPassword.length() == 0) {
                editName.error = getString(R.string.required_field_username)
                editEmail.error = getString(R.string.required_field_email)
                editPassword.setError(getString(R.string.required_field_password), null)
                regisButton.isEnabled = false
            } else if (editName.length() != 0 && editEmail.length() != 0 && editPassword.length() != 0) {
                regisButton.isEnabled = true
            }

            editEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    regisButton.isEnabled = editEmail.text!!.isNotEmpty() && editPassword.text!!.isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    regisButton.isEnabled = editPassword.length() >= 8
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            regisButton.setOnClickListener {
                showLoading()
                postText()
                showToast()
                moveActivity()
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }
    

    // Button Back
    private fun btnBack() {
        binding.ivBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    // Button Login
    private fun btnLogin() {
        binding.tvLogin2.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading() {
        registerViewModel.isLoading.observe(this@RegisterActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        registerViewModel.toastText.observe(this@RegisterActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@RegisterActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun moveActivity() {
        registerViewModel.registerResponse.observe(this@RegisterActivity) { response ->
            if (response.success) {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun postText() {
        binding.apply {
            registerViewModel.doRegister(
                editName.text.toString(),
                editEmail.text.toString(),
                editPassword.text.toString(),
            )
        }
    }
}