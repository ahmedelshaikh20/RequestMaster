package com.example.instabugtask.api.model

import com.example.instabugtask.domain.model.ResponseInfo

data class Response(
  val id: Int = 0,
  val requestUrl: String?,
  val requestBody: String?,
  val responseStatus: String?,
  val responseBody: String?,
  val requestType: String?,
  val executionTime: String?,
  val responseStatusCode: Int?,
  val requestHeaders: String?,
  val responseHeaders: String?,
  val queryParameters: String?,
  val error: String?,
)


fun Response.toDomain(): ResponseInfo {
  return ResponseInfo(
    id,
    requestUrl,
    requestBody,
    responseStatus,
    responseBody,
    requestType,
    executionTime,
    responseStatusCode,
    requestHeaders,
    responseHeaders,
    queryParameters,
    error
  )

}
