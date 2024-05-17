package com.example.instabugtask

import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.domain.repository.Repository
import org.json.JSONObject

class FakeRepository : Repository {
  override fun executeGetApiRequest(
    inputUrl: StringBuilder,
    headers: Map<String, String>,
    querys: Map<String, String>?
  ): ResponseInfo {
    return ResponseInfo(
      requestBody = "",
      responseStatusCode = 200,
      responseBody = "Mock Response",
      requestType = "GET",
      executionTime = "100 ms",
      requestUrl = inputUrl.toString(),
      requestHeaders = headers.toString(),
      error = "",
      queryParameters = querys.toString(),
      responseHeaders = "",
      responseStatus = "Success"
    )
  }

  override fun executePostApiRequest(
    inputUrl: String,
    headers: Map<String, String>,
    requestBody: JSONObject?
  ): ResponseInfo {
    return ResponseInfo(
      requestBody = requestBody.toString(),
      responseStatusCode = 200,
      responseBody = "Mock Response",
      requestType = "POST",
      executionTime = "100 ms",
      requestUrl = inputUrl,
      requestHeaders = headers.toString(),
      error = "",
      queryParameters = "",
      responseHeaders = "",
      responseStatus = "Success"
    )
  }

  override fun executePostApiRequestWithFile(
    inputUrl: String,
    fileName: String?,
    headers: Map<String, String>
  ): ResponseInfo {
    return ResponseInfo(
      requestBody = fileName ?: "",
      responseStatusCode = 404,
      responseBody = "Mock Response",
      requestType = "POST",
      executionTime = "100 ms",
      requestUrl = "",
      requestHeaders = headers.toString(),
      error = "",
      queryParameters = "",
      responseHeaders = "",
      responseStatus = "Success"
    )
  }

  override fun getAllLogs(): List<ResponseInfo> {
return createListResponse()
  }

  override fun filterLogs(
    requestTypes: List<String>,
    responseStatuses: List<String>
  ): List<ResponseInfo> {
return emptyList() }
}

fun createListResponse(): List<ResponseInfo> {
  val responseList = mutableListOf<ResponseInfo>()

  // Create and add ResponseInfo objects to the list
  val response1 = ResponseInfo(
    requestBody = "requestBody1",
    responseStatusCode = 200,
    responseBody = "responseBody1",
    requestType = "GET",
    executionTime = "100 ms",
    requestUrl = "https://example.com/api/endpoint1",
    requestHeaders = mapOf("Header1" to "Value1").toString(),
    error = "",
    queryParameters = "",
    responseHeaders = mapOf("Header2" to "Value2").toString(),
    responseStatus = "Success"
  )
  responseList.add(response1)

  val response2 = ResponseInfo(
    requestBody = "requestBody2",
    responseStatusCode = 400,
    responseBody = "responseBody2",
    requestType = "POST",
    executionTime = "200 ms",
    requestUrl = "https://example.com/api/endpoint2",
    requestHeaders = mapOf("Header3" to "Value3").toString(),
    error = "Error Message",
    queryParameters = "",
    responseHeaders = mapOf("Header4" to "Value4").toString(),
    responseStatus = "Fail"
  )
  responseList.add(response2)

  val response3 = ResponseInfo(
    requestBody = "requestBody3",
    responseStatusCode = 404,
    responseBody = "responseBody3",
    requestType = "PUT",
    executionTime = "150 ms",
    requestUrl = "https://example.com/api/endpoint3",
    requestHeaders = mapOf("Header5" to "Value5").toString(),
    error = "Not Found",
    queryParameters = "param1=value1&param2=value2",
    responseHeaders = mapOf("Header6" to "Value6").toString(),
    responseStatus = "Fail"
  )
  responseList.add(response3)

  val response4 = ResponseInfo(
    requestBody = "requestBody4",
    responseStatusCode = 201,
    responseBody = "responseBody4",
    requestType = "DELETE",
    executionTime = "180 ms",
    requestUrl = "https://example.com/api/endpoint4",
    requestHeaders = mapOf("Header7" to "Value7").toString(),
    error = "",
    queryParameters = "param3=value3",
    responseHeaders = mapOf("Header8" to "Value8").toString(),
    responseStatus = "Success"
  )
  responseList.add(response4)
   return responseList
}
