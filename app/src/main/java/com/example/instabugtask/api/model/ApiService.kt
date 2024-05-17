package com.example.instabugtask.api.model

import com.example.instabugtask.domain.model.ResponseInfo
import org.json.JSONObject

interface ApiService {

  fun createApiGetRequest(
    inputUrl: StringBuilder,
    headers: Map<String, String>,
    queries: Map<String, String>?
  ): ResponseInfo


  fun createApiPostRequest(
    inputUrl: String,
    headers: Map<String, String>,
    requestBody: JSONObject?
  ): ResponseInfo

  fun createApiPostRequestWithFileUpload(
    inputUrl: String,
    filePath: String?,
    headers: Map<String, String>
  ): ResponseInfo
}
