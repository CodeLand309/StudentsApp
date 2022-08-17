package com.example.studentsapp.data.model

import com.example.studentsapp.dependency.AppModule
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

data class User(
    @SerializedName("name") val name: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("userType") val userType: String = AppModule.UserType.Student.name,
    @SerializedName("id") val id: Long=-1,
)