package com.example.roadtofit.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.roadtofit.R
import com.example.roadtofit.data.model.User
import com.example.roadtofit.data.model.ViewModelFactory
import com.example.roadtofit.databinding.ActivityLoginBinding
import com.example.roadtofit.ui.main.MainActivity
import com.example.roadtofit.ui.WelcomeActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()
        setupViewModel()
        btnBack()
        btnRegis()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.signIn, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val back = ObjectAnimator.ofFloat(binding.back, View.ALPHA, 1f).setDuration(100)
        val judul1 = ObjectAnimator.ofFloat(binding.title1, View.ALPHA, 1f).setDuration(100)
        val judul2 = ObjectAnimator.ofFloat(binding.title2, View.ALPHA, 1f).setDuration(100)
        val title1 = ObjectAnimator.ofFloat(binding.signIn, View.ALPHA, 1f).setDuration(100)
        val title2 = ObjectAnimator.ofFloat(binding.editEmail, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.tlPassword, View.ALPHA, 1f).setDuration(100)
        val password = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(100)


        val together = AnimatorSet().apply {
            playTogether(email, password, login)
        }

        AnimatorSet().apply {
            playSequentially(back, title1, title2, together,judul1, judul2)
            startDelay = 200
        }.start()
    }

    private fun setupAction() {
        binding.apply {
            if (editEmail.length() == 0 && editPassword.length() == 0) {
                editEmail.error = getString(R.string.required_field_email)
                editPassword.setError(getString(R.string.required_field_password), null)
                btnSignIn.isEnabled = false
            } else if (editEmail.length() != 0 && editPassword.length() != 0) {
                btnSignIn.isEnabled = true
            }

            editEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    btnSignIn.isEnabled = editEmail.text!!.isNotEmpty() && editPassword.text!!.isNotEmpty()
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    btnSignIn.isEnabled = editPassword.length() >= 8
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            btnSignIn.setOnClickListener {
                showLoading()
                postText()
                showToast()
                loginViewModel.login()
                moveActivity()
            }
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun showToast() {
        loginViewModel.toastText.observe(this@LoginActivity) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this@LoginActivity, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoading() {
        loginViewModel.isLoading.observe(this@LoginActivity) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun postText() {
        binding.apply {
            loginViewModel.doLogin(
                editEmail.text.toString(),
                editPassword.text.toString()
            )
        }

        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            saveSession(
                User(
                    response.user?.gender.toString(),
                    response.user?.name.toString(),
                    response.token,
                    response.user?.gender.toString(),
                    true
                )
            )
            loginViewModel.saveId(response.user?.userId.toString())
        }
    }

    private fun moveActivity() {
        loginViewModel.loginResponse.observe(this@LoginActivity) { response ->
            Log.d ("LoginActivity", "response: $response")
            if (response.success) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveSession(session: User){
        loginViewModel.saveSession(session)
    }

    private fun btnBack() {
        binding.back.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun btnRegis() {
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}