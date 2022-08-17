package com.example.studentsapp.data.repos

import android.util.Log
import com.example.studentsapp.data.model.User
import com.example.studentsapp.data.network.ApiInterface
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

const val TAG = "Repository"

@Singleton
class Repository @Inject constructor(
    private val restApiInterface: ApiInterface
) {

    //Not Used Yet
    fun getUserAsync(userType: String): Deferred<List<User>> {
        val result = CoroutineScope(Dispatchers.IO).async {
            val response = restApiInterface.fetchUsers(userType)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()!!
            }
            else {
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                emptyList()
            }
        }
        return result
    }

    fun createStudentAccountAsync(user: User): Deferred<User?> {
        //var userDetail: User? = null
        val result = CoroutineScope(Dispatchers.IO).async {
            val response = restApiInterface.createStudentAccount(user)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()!!
            }
            else {
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                null
            }
        }
        return result
    }

    fun requestTeacherAccountAsync(user: User): Deferred<User?> {
        //var userDetail: User? = null
        val result = CoroutineScope(Dispatchers.IO).async {
            val response = restApiInterface.requestTeacherAccount(user)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()!!
            }
            else {
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                null
            }
        }
        return result
    }

    fun authenticateUserAsync(username: String, password: String): Deferred<User?> {
        val result = CoroutineScope(Dispatchers.IO).async {
            val response = restApiInterface.authenticateUser(username, password)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()
            }else{
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                null
            }
        }
        return result
    }

    //Not Used Yet
    fun upgradeUser(id: Long): Deferred<Boolean?> {
        val result = CoroutineScope(Dispatchers.IO).async{
            val response = restApiInterface.upgradeUser(id)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()
            }else{
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                null
            }
        }
        return result
    }

    //Not Used Yet
    fun updateUserDetails(user: User): Deferred<User?> {
        val result = CoroutineScope(Dispatchers.IO).async{
            val response = restApiInterface.updateUserDetails(user)
            if(response.isSuccessful){
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                response.body()
            }else{
                Log.d(TAG, "Response code: ${response.code()}")
                Log.d(TAG, "Response message: ${response.message()}")
                Log.d(TAG, "Response error body: ${response.errorBody()}")
                null
            }
        }
        return result
    }
}