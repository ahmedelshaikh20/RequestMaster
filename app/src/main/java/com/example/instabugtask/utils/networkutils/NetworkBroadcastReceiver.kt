package com.example.instabugtask.utils.networkutils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast


class NetworkBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(p0: Context?, p1: Intent?) {
    val connectivityManager =
      p0?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.registerDefaultNetworkCallback(object :
      ConnectivityManager.NetworkCallback() {
      override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Toast.makeText(p0, "Internet is Available,You Can Test Requests", Toast.LENGTH_SHORT).show()
      }

      override fun onLost(network: Network) {
        super.onLost(network)
        Toast.makeText(
          p0,
          "Internet is not Available,Check Internet Connection",
          Toast.LENGTH_SHORT
        ).show()

      }
    })
  }

}
