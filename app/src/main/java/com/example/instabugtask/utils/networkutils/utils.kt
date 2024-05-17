package com.example.instabugtask.utils.networkutils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.instabugtask.MainActivity


fun checkNetworkAvailability(context: Context): Boolean {

  // register activity with the connectivity manager service
  val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  val network = connectivityManager.activeNetwork ?: return false
  val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
  return when {
    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)  -> true
    else -> false
  }
}


private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 101
fun checkStoragePermission(activity: MainActivity) :Boolean{
  return if (ContextCompat.checkSelfPermission(
      activity,
      Manifest.permission.READ_EXTERNAL_STORAGE
    ) != PackageManager.PERMISSION_GRANTED
  ) {
    // Permission is not granted, request it
    ActivityCompat.requestPermissions(
      activity,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      READ_EXTERNAL_STORAGE_REQUEST_CODE
    )
    false
  } else {
    true
  }
}

