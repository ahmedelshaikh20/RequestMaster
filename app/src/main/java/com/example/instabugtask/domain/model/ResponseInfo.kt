package com.example.instabugtask.domain.model

import androidx.compose.ui.text.capitalize
import com.example.instabugtask.ui.screens.main.MainScreenState

data class ResponseInfo(
  val id: Int=0,
  val requestUrl: String?,
  val requestBody: String?,
  val responseStatus : String?,
  val responseBody: String?,
  val requestType: String?,
  val executionTime: String?,
  val responseStatusCode: Int?,
  val requestHeaders : String?,
  val responseHeaders:String?,
  val queryParameters : String?,
  val error : String?,
  val filePath : String?

)


