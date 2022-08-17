package com.example.studentsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class StudentApplication : Application() {

//    fun isNetworkAvailable(): Boolean{
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            val network = connectivityManager.activeNetwork ?: return false
//            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//            return when {
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//                else -> false
//            }
//        } else {
//            connectivityManager.activeNetworkInfo?.isConnected ?: false
//        }
//    }
}