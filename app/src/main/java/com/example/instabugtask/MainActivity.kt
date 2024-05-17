package com.example.instabugtask

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.instabugtask.api.ApiService
import com.example.instabugtask.database.DatabaseHelper
import com.example.instabugtask.navigation.Navigation
import com.example.instabugtask.repository.AppRepositoryImpl
import com.example.instabugtask.ui.theme.InstabugTaskTheme
import com.example.instabugtask.utils.networkutils.NetworkBroadcastReceiver
import com.example.instabugtask.utils.networkutils.NetworkUtils
import com.example.instabugtask.viewmodel.LogsViewModel
import com.example.instabugtask.viewmodel.LogsViewModelFactory
import com.example.instabugtask.viewmodel.MainViewModel
import com.example.instabugtask.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
  val networkBroadcastReceiver = NetworkBroadcastReceiver()


  override fun onCreate(savedInstanceState: Bundle?) {

    NetworkUtils.init(this.applicationContext)

    super.onCreate(savedInstanceState)

    val dbHandler = DatabaseHelper(this)
    val apiService = ApiService()
    val repository = AppRepositoryImpl(apiService, dbHandler)
    val mainViewModelFactory = MainViewModelFactory(repository)
    val mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    val logsViewModelFactory = LogsViewModelFactory(repository)
    val logViewModel = ViewModelProvider(this, logsViewModelFactory)[LogsViewModel::class.java]

    setContent {
      InstabugTaskTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          Image(
            painter = painterResource(id = R.drawable.topography),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
              .background(colorResource(id = R.color.background))
          )
//          Navigation(viewModel = mainViewModel , logViewModel)
          Navigation(mainViewModel, logViewModel)
        }
      }
    }
  }


  override fun onStart() {
    super.onStart()
    val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    registerReceiver(networkBroadcastReceiver, intentFilter)
  }

  override fun onDestroy() {
    super.onDestroy()
    // We should unregisster the receiver to avoid memory leaks
    unregisterReceiver(networkBroadcastReceiver)
  }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  InstabugTaskTheme {
    Greeting("Android")
  }
}

//Testing Get Request with 500 error code
//apiService.createApiGetRequest(StringBuilder("https://httpbin.org/status/500") , headers = headers , mapOf())

//Testing GET REQUEST WITH QUERY PARAMETERS
//val querys = mapOf(
//  "page" to "2"
//)
//apiService.createApiGetRequest(StringBuilder("https://reqres.in/api/users") , headers = headers , querys)

// POST with File Upload
//apiService.createApiPostRequestWithFileUpload(this,"https://httpbin.org/post" , headers = headers , fileName = "lol.txt")
