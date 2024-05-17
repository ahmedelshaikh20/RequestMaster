package com.example.instabugtask.utils.networkutils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {

  private lateinit var applicationContext: Context
  fun init(context: Context) {
    applicationContext = context.applicationContext
  }

  fun checkNetworkAvailability(): Boolean {
    val connectivityManager =
      applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
    return when {
      activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(
        NetworkCapabilities.TRANSPORT_CELLULAR
      ) -> true

      else -> false
    }
  }
}



