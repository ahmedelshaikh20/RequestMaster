package com.example.instabugtask.repository

import android.util.Log
import com.example.instabugtask.api.ApiService
import com.example.instabugtask.database.DatabaseHelper
import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.domain.repository.Repository
import org.json.JSONObject

class AppRepositoryImpl(val apiService: ApiService, val databaseHelper: DatabaseHelper) :
  Repository {
  override fun executeGetApiRequest(
    inputUrl: StringBuilder,
    headers: Map<String, String>,
    querys: Map<String, String>?
  ): ResponseInfo {
    val response = apiService.createApiGetRequest(inputUrl, headers, querys)
    databaseHelper.addLog(response)
    Log.d("Successfully Added to the database", response.toString())

    return response

  }

  override fun executePostApiRequest(
    inputUrl: String,
    headers: Map<String, String>,
    requestBody: JSONObject?
  ): ResponseInfo {
    val response = apiService.createApiPostRequest(inputUrl, headers, requestBody)
    databaseHelper.addLog(response)
    Log.d("Successfully Added to the database", response.toString())
    return response
  }

  override fun executePostApiRequestWithFile(
    inputUrl: String,
    fileName: String?,
    headers: Map<String, String>
  ): ResponseInfo {
    val response = apiService.createApiPostRequestWithFileUpload(inputUrl, fileName, headers)
    databaseHelper.addLog(response)
    Log.d("Successfully Added to the database", response.toString())
    return response
  }

  override  fun getAllLogs(): List<ResponseInfo> {
    return databaseHelper.getAllLogs()
  }

  override fun filterLogs(
    requestTypes: List<String>,
    responseStatuses: List<String>
  ): List<ResponseInfo> {
    return databaseHelper.filterLogs(requestTypes, responseStatuses)
  }
}
