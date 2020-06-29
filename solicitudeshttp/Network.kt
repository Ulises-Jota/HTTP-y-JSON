package com.example.solicitudeshttp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class Network {

    // Crear un método que pueda ser llamado de forma estática (a través del companion object)
    // para que pueda ser reutilizado y que sirva para validar si hay red.
    companion object{
        fun hayRed(activity: AppCompatActivity): Boolean{
            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}