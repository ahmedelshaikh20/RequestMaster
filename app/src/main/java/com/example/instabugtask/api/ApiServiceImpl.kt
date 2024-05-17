package com.example.instabugtask.api

import android.net.Uri
import android.util.Log
import com.example.instabugtask.api.model.ApiService
import com.example.instabugtask.api.model.Response
import com.example.instabugtask.api.model.toDomain
import com.example.instabugtask.domain.model.ResponseInfo
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


class ApiServiceImpl : ApiService {

  //GET REQUEST WITH QUERY PARAMETERS
  override fun createApiGetRequest(
    inputUrl: StringBuilder,
    headers: Map<String, String>,
    queries: Map<String, String>?
  ): ResponseInfo {
    val httpURLConnection: HttpURLConnection
    // Add Querys before we start the connection
    if (!queries.isNullOrEmpty()) {
      inputUrl.append("?")
      for ((key, value) in queries) {
        inputUrl.append("$key=$value&")
      }
    }
    val url = URL(inputUrl.toString())
    val startTime = System.currentTimeMillis()

    httpURLConnection = url.openConnection() as HttpURLConnection


    // Add request headers
    headers.forEach { (key, value) ->
      httpURLConnection.setRequestProperty(key, value)
    }

    val requestHeaders = httpURLConnection.requestProperties.toString()
    val resCode = httpURLConnection.responseCode
    Log.d("Response Code", resCode.toString())

    val endTime = System.currentTimeMillis()
    var responseBody = ""
    var error = ""
    val responseCode = httpURLConnection.responseCode
    val responseStatus =
      if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 201 || httpURLConnection.responseCode == 204) {
        responseBody = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
        "Success"

      } else {
        error = httpURLConnection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""

        "Fail"
      }

    val response = Response(
      requestBody = "",
      responseStatusCode = httpURLConnection.responseCode,
      responseBody = responseBody,
      requestType = httpURLConnection.requestMethod,
      executionTime = (endTime - startTime).toString(),
      requestUrl = url.toString(),
      requestHeaders = requestHeaders,
      error = error,
      queryParameters = queries.toString(),
      responseHeaders = httpURLConnection.headerFields.toString(),
      responseStatus = responseStatus,
      filePath = ""
    )
    httpURLConnection.disconnect()

    return response.toDomain()


  }

  override fun createApiPostRequest(
    inputUrl: String,
    headers: Map<String, String>,
    requestBody: JSONObject?
  ): ResponseInfo {
    //https://httpbin.org/post
    val url = URL(inputUrl)
    val httpURLConnection = url.openConnection() as HttpURLConnection
    httpURLConnection.requestMethod = "POST"
    val startTime = System.currentTimeMillis()

    // Add request headers
    headers.forEach { (key, value) ->
      httpURLConnection.setRequestProperty(key, value)
    }
    val requestHeaders = httpURLConnection.requestProperties.toString()


    // Send request body
    val out: OutputStream = BufferedOutputStream(httpURLConnection.outputStream)
    out.use { stream ->
      stream.write(requestBody.toString().toByteArray())
    }

    val endTime = System.currentTimeMillis()     //Request End time


    // Read response
    var responseBody = ""
    var error = ""
    val responseCode = httpURLConnection.responseCode
    val responseStatus =
      if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 201 || httpURLConnection.responseCode == 204) {
        responseBody = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
        "Success"

      } else {
        error = if (responseCode == HttpURLConnection.HTTP_OK) "No Error in the Request" else {
          httpURLConnection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""
        }
        "Fail"
      }


    val response = Response(
      requestBody = requestBody?.toString() ?: "",
      responseStatusCode = responseCode,
      responseBody = responseBody,
      requestType = "POST",
      executionTime = (endTime - startTime).toString(),
      requestUrl = inputUrl,
      requestHeaders = requestHeaders,
      error = error,
      queryParameters = "",
      responseHeaders = httpURLConnection.headerFields.toString(),
      responseStatus = responseStatus,
      filePath = ""
    )

    httpURLConnection.disconnect()
    return response.toDomain()


  }

  override fun createApiPostRequestWithFileUpload(
    inputUrl: String,
    fileStream: InputStream,
    headers: Map<String, String>,
    mime: String,
    fileUri : Uri
  ): ResponseInfo {
    val httpURLConnection: HttpURLConnection

    val startTime = System.currentTimeMillis()   //Request Start time

    val url = URL(inputUrl)
    httpURLConnection = url.openConnection() as HttpURLConnection
    // Log.d("File Path", filePath.toString())
    httpURLConnection.requestMethod = "POST"

    httpURLConnection.setRequestProperty("Content-Type", mime)

    // Add request headers
    headers.forEach { (key, value) ->
      httpURLConnection.setRequestProperty(key, value)
    }

    val requestHeaders = httpURLConnection.requestProperties.toString()
    // To ensure connection will be used to send content
    httpURLConnection.doInput = true
    httpURLConnection.doOutput = true
    //Send Request Body
    val requestBody = StringBuilder()

      val out: OutputStream = BufferedOutputStream(httpURLConnection.outputStream)
      // Then We read all the data of the file stream and write it to outputstream
      var byteRead = fileStream.read()
      while (byteRead != -1) {
        requestBody.append(byteRead.toChar())
        // Read the next byte
        out.write(byteRead)
        byteRead = fileStream.read()
      }
      out.close()
      fileStream.close()

    val endTime = System.currentTimeMillis()     //Request End time
    var responseBody = ""
    var error = ""
    val responseCode = httpURLConnection.responseCode
    val responseStatus =
      if (httpURLConnection.responseCode == 200 || httpURLConnection.responseCode == 201 || httpURLConnection.responseCode == 204) {
        responseBody = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
        "Success"
      } else {
        error = if (responseCode == HttpURLConnection.HTTP_OK) "No Error in the Request" else {
          httpURLConnection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""
        }
        "Fail"
      }

    val response = Response(
      requestBody = requestBody.trim().toString(),
      responseStatusCode = httpURLConnection.responseCode,
      responseBody = responseBody.toString(),
      requestType = httpURLConnection.requestMethod,
      executionTime = (endTime - startTime).toString(),
      requestUrl = url.toString(),
      requestHeaders = requestHeaders,
      error = error,
      queryParameters = "",
      responseHeaders = httpURLConnection.headerFields.toString(),
      responseStatus = responseStatus,
      filePath = fileUri.toString()
    )

    httpURLConnection.disconnect()
    return response.toDomain()

  }


}

