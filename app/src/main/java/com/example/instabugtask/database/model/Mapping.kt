package com.example.instabugtask.database.model

import android.annotation.SuppressLint
import android.database.Cursor
import com.example.instabugtask.database.RequestsTable
import com.example.instabugtask.domain.model.ResponseInfo

@SuppressLint("Range")
fun Cursor.toDomain(): ResponseInfo {
  return ResponseInfo(
    id = this.getInt(this.getColumnIndex(RequestsTable.ID)),
    requestUrl = this.getString(this.getColumnIndex(RequestsTable.REQUEST_URL)),
    requestBody = this.getString(this.getColumnIndex(RequestsTable.REQUEST_BODY)),
    responseBody = this.getString(this.getColumnIndex(RequestsTable.RESPONSE_BODY)),
    requestType = this.getString(this.getColumnIndex(RequestsTable.REQUEST_TYPE)),
    executionTime = this.getString(this.getColumnIndex(RequestsTable.EXECUTION_TIME)),
    responseStatusCode = this.getInt(this.getColumnIndex(RequestsTable.RESPONSE_STATUS_CODE)),
    requestHeaders = this.getString(this.getColumnIndex(RequestsTable.REQUEST_HEADERS)),
    responseHeaders = this.getString(this.getColumnIndex(RequestsTable.RESPONSE_HEADERS)),
    queryParameters = this.getString(this.getColumnIndex(RequestsTable.QUERY_PARAMETERS)),
    error = this.getString(this.getColumnIndex(RequestsTable.ERROR)),
    responseStatus = this.getString(this.getColumnIndex(RequestsTable.RESPONSE_STATUS)),
    filePath = this.getString(this.getColumnIndex(RequestsTable.FILE_PATH)),

    )
}

fun ResponseInfo.toDb(): ResponseDb {
  return ResponseDb(
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
    error,
    filePath
  )
}
