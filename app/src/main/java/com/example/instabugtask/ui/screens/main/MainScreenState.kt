package com.example.instabugtask.ui.screens.main

import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.ui.screens.main.model.RequestType

data class MainScreenState(
  val url: String,
  val requestType: RequestType = RequestType.GetRequest,
  val headers: Map<String, String>,
  //
  val queries: Map<String, String>,
  val currentHeader: String,
  val currentKey: String,
  val currentRequest: ResponseInfo? = null,
  //
  val queryParameter: String = "",
  val queryValue: String = "",
  val filePath: String? = null,
  //
  val requestBodyMap: Map<String, String>,
  val currentJsonKey: String,
  val currentJsonValue: String,
  val showToastMessage: Boolean = false,
  val currentToastMessage: String? = null,
  //
  val isAddParameterClicked: Boolean,
  val isAddJsonBodyClicked: Boolean,
  val isChooseFileClicked: Boolean
)

fun getEmptyState(): MainScreenState {
  return MainScreenState(
    "",
    headers = mapOf(),
    queries = mapOf(),
    currentHeader = "",
    currentKey = "",
    currentRequest = null,
    queryParameter = "",
    filePath = "",
    showToastMessage = false,
    currentToastMessage = "",
    requestBodyMap = mapOf(),
    currentJsonKey = "",
    currentJsonValue = "",
    isAddJsonBodyClicked = false,
    isAddParameterClicked = false,
    isChooseFileClicked = false
  )
}


