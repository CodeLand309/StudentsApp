package com.example.studentsapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.studentsapp.R
import com.example.studentsapp.data.model.User
import com.example.studentsapp.databinding.ActivityRegisterBinding
import com.example.studentsapp.dependency.AppModule
import com.example.studentsapp.ui.activities.viewmodels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var name: String
    private lateinit var dob: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var gender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStudAccount.setOnClickListener {
                readUserInput(AppModule.UserType.Student)
            }

            btnTeachAccount.setOnClickListener {
                readUserInput(AppModule.UserType.Unknown)
            }

            val spinner: Spinner = spinnerGender
            ArrayAdapter.createFromResource(
                this@RegisterActivity,
                R.array.gender_spinner,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                spinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            gender = parent?.getItemAtPosition(position).toString()
                            Log.d("Main", "gender: $gender")
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            showSnackBar("Please Select Gender", LENGTH_SHORT)
                        }
                    }
            }
        }

        lifecycleScope.launch(Dispatchers.Unconfined) {
            viewModel.eventsFlow.collect{ event ->
                when(event){
                    is RegisterViewModel.CreateAccountEvent.CreateStudentAccountEvent -> {
                        handleAccountCreationEvent(event.id, AppModule.UserType.Student)
                    }
                    is RegisterViewModel.CreateAccountEvent.InitiateTeacherAccountRequestEvent -> {
                        handleAccountCreationEvent(event.id, AppModule.UserType.Teacher)
//                        event.apply {
//                            if(user!=null) {
//                                user.apply {
//                                    withContext(Dispatchers.Main) {
//                                        moveToHomeActivity(name, dob, gender, username, password)
//                                    }
//                                }
//                            }else{
//                                withContext(Dispatchers.Main){
//                                    showSnackBar("Failed to Create Account", LENGTH_SHORT)
//                                }
//                            }
//                        }
                    }
                }
            }
        }
    }

    private suspend fun handleAccountCreationEvent(id: Long?, userType: AppModule.UserType){
        if(id!=null) {
            withContext(Dispatchers.Main) {
                if(userType == AppModule.UserType.Teacher) {
                    showSnackBar("Teacher Account Request Initiated", LENGTH_SHORT)
                }else{
                    showSnackBar("Account Created Successfully", LENGTH_SHORT)
                }
                delay(1000)
                moveToHomeActivity(id/* name, dob, gender, username, password*/)
            }
        }else{
            withContext(Dispatchers.Main){
                showSnackBar("Failed to Create Account", LENGTH_SHORT)
            }
        }
    }

    private fun readUserInput(type: AppModule.UserType){
        binding.apply {
            val name: String = edTextName.text.toString()
            val dob: String = edTextDOB.text.toString()
            val username = edTextNewUsername.text.toString()
            val password = edTextNewPassword.text.toString()

            Log.d("Register Activity", "name: $name")
            Log.d("Register Activity", "name: $dob")
            Log.d("Register Activity", "name: $gender")
            Log.d("Register Activity", "name: $username")
            Log.d("Register Activity", "name: $password")

            if (name.isEmpty() || dob.isEmpty() || gender.isEmpty() || username.isEmpty() || password.isEmpty())
                showSnackBar("Please enter all the values", LENGTH_SHORT)
            else {
                if(type == AppModule.UserType.Student){
                    val user = User(name, dob, gender, username, password, AppModule.UserType.Student.name)
                    viewModel.createStudentAccount(user)
                }else{
                    val user = User(name, dob, gender, username, password, AppModule.UserType.Unknown.name)
                    viewModel.initiateTeacherAccountRequest(user)
                }
            }
        }
    }

    private fun moveToHomeActivity(id: Long, /*name: String, dob: String, gender: String, username: String, password: String*/) {
        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
        intent.apply {
            putExtra("ID", id)
//            putExtra("Name", name)
//            putExtra("DOB", dob)
//            putExtra("Gender", gender)
//            putExtra("Username", username)
//            putExtra("Password", password)
            startActivity(this)
        }
    }

    private fun showSnackBar(msg: String, duration: Int){
        Snackbar.make(binding.root, msg, duration).show()
    }
}