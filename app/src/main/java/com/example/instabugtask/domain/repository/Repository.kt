package com.example.instabugtask.domain.repository

import android.net.Uri
import com.example.instabugtask.domain.model.ResponseInfo
import org.json.JSONObject
import java.io.InputStream

interface Repository {
   fun executeGetApiRequest(
    inputUrl: StringBuilder,
    headers: Map<String, String>,
    querys: Map<String, String>?
  ): ResponseInfo

  fun executePostApiRequest(
    inputUrl: String,
    headers: Map<String, String>,
    requestBody: JSONObject?
  ): ResponseInfo

   fun executePostApiRequestWithFile(
     inputUrl: String,
     fileStream: InputStream,
     headers: Map<String, String>,
     mime: String,
     fileUri : Uri
  ): ResponseInfo


   fun getAllLogs(): List<ResponseInfo>


   fun filterLogs(requestTypes: List<String>, responseStatuses: List<String>):List<ResponseInfo>
}
