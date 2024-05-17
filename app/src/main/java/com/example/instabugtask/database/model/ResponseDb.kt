package com.example.instabugtask.database.model

data class ResponseDb(
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
)
