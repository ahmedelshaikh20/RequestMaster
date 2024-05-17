package com.example.instabugtask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instabugtask.ui.screens.logscreen.LogScreen
import com.example.instabugtask.ui.screens.main.MainScreen
import com.example.instabugtask.viewmodel.LogsViewModel
import com.example.instabugtask.viewmodel.MainViewModel

@Composable
fun Navigation( viewModel: MainViewModel, logsViewModel:LogsViewModel) {


  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
    composable(Screen.MainScreen.route) { MainScreen(viewModel , navController) }
    composable(Screen.LogScreen.route) { LogScreen(logsViewModel, navController) }



  }
}
