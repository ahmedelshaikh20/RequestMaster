package com.example.instabugtask.api.model

import android.net.Uri
import com.example.instabugtask.domain.model.ResponseInfo
import org.json.JSONObject
import java.io.InputStream

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
    fileStream: InputStream,
    headers: Map<String, String>,
    mime: String,
    fileUri : Uri
  ): ResponseInfo
}
