package com.example.studentsapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.studentsapp.data.preferences.PreferencesManager
import com.example.studentsapp.databinding.ActivityLoginBinding
import com.example.studentsapp.ui.activities.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    //val TAG = "Main Activity"
    //lateinit var text: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnLogin.setOnClickListener{
                val userName = edTextUserName.text.toString()
                val password = edTextPassword.text.toString()
                viewModel.authenticateUser(userName, password)
                //startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            textRegisterPageLink.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }

        lifecycleScope.launch(Dispatchers.Unconfined) {
            viewModel.eventsFlow.collect(){ event ->
                when(event){
                    is LoginViewModel.LoginPageEvent.UserLogin -> {
                        handleLoginResult(event.id, event.status)
                    }
                    else -> {}
                }
            }
        }
    }

    private suspend fun handleLoginResult(id: Long?, status: PreferencesManager.UserLoginStatus) {
        if (status == PreferencesManager.UserLoginStatus.SUCCESS && (id?.equals(-1) == false)) {
            showSnackBar("Login Successful", Snackbar.LENGTH_SHORT)
            delay(1000)
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            Log.d("LoginActivity", "id: $id")
            intent.apply {
                putExtra("ID", id)
                startActivity(this)
            }
        }else{
            showSnackBar("Invalid Credentials", Snackbar.LENGTH_SHORT)
        }
    }

    private fun showSnackBar(msg: String, duration: Int){
        Snackbar.make(binding.root, msg, duration).show()
    }

}