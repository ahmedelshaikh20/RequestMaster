package com.example.instabugtask.navigation

sealed class Screen(val route: String) {

  object MainScreen : Screen("main_screen")
  object LogScreen : Screen("signup_screen")

//  fun withArgs(vararg args: String): String {
//    return buildString {
//      append(route)
//      args.forEach { arg ->
//        append(("/$arg"))
//      }
//    }
//  }
}
