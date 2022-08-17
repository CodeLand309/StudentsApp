package com.example.studentsapp.data.network

import com.example.studentsapp.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("/getUsers")
    suspend fun fetchUsers(@Header("userType") type: String): Response<List<User>>

    @POST("/createAccount")
    suspend fun createStudentAccount(@Body user: User): Response<User>

    @POST("/requestTeacherAccount")
    suspend fun requestTeacherAccount(@Body user: User): Response<User>

    @GET("/authenticateUser")
    suspend fun authenticateUser(@Header("userName") username: String, @Header("password") password: String): Response<User>

    @PUT("/upgradeUser")
    suspend fun upgradeUser(@Header("id") id: Long): Response<Boolean>

    @PUT("/updateUserDetails")
    suspend fun updateUserDetails(@Body user: User): Response<User>

}