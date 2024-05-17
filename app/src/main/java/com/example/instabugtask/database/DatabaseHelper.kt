package com.example.instabugtask.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.instabugtask.database.RequestsTable.CREATE_TABLE
import com.example.instabugtask.database.RequestsTable.DB_NAME
import com.example.instabugtask.database.RequestsTable.DB_VERSION
import com.example.instabugtask.database.RequestsTable.DROP_TABLE
import com.example.instabugtask.database.RequestsTable.ERROR
import com.example.instabugtask.database.RequestsTable.EXECUTION_TIME
import com.example.instabugtask.database.RequestsTable.FILE_PATH
import com.example.instabugtask.database.RequestsTable.QUERY_PARAMETERS
import com.example.instabugtask.database.RequestsTable.REQUEST_BODY
import com.example.instabugtask.database.RequestsTable.REQUEST_HEADERS
import com.example.instabugtask.database.RequestsTable.REQUEST_TYPE
import com.example.instabugtask.database.RequestsTable.REQUEST_URL
import com.example.instabugtask.database.RequestsTable.RESPONSE_BODY
import com.example.instabugtask.database.RequestsTable.RESPONSE_HEADERS
import com.example.instabugtask.database.RequestsTable.RESPONSE_STATUS
import com.example.instabugtask.database.RequestsTable.RESPONSE_STATUS_CODE
import com.example.instabugtask.database.RequestsTable.TABLE_NAME
import com.example.instabugtask.database.model.ResponseDb
import com.example.instabugtask.database.model.toDb
import com.example.instabugtask.database.model.toDomain
import com.example.instabugtask.domain.model.ResponseInfo

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
  override fun onCreate(db: SQLiteDatabase?) {
    db?.execSQL(CREATE_TABLE)
  }

  override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    db?.execSQL(DROP_TABLE)
    onCreate(db)
  }


  fun getAllLogs(): List<ResponseInfo> {
    val apiLogs = ArrayList<ResponseInfo>()
    val db = writableDatabase
    val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          apiLogs.add(cursor.toDomain())
        } while (cursor.moveToNext())
      }
    }
    cursor.close()
    return apiLogs
  }


  fun addLog(response: ResponseInfo): Boolean {
    val db = writableDatabase
    val values = fillValues(response.toDb())
    val result = db.insert(TABLE_NAME, null, values) != -1L
    db.close()
    return result
  }

  fun filterLogs(requestTypes: List<String>, responseStatuses: List<String>): List<ResponseInfo> {
    val apiLogs = ArrayList<ResponseInfo>()
    val db = writableDatabase
    val selection = StringBuilder()
    val selectionArgs = mutableListOf<String>()

    // Build the selection clause based on request types
    if (requestTypes.isNotEmpty()) {
      selection.append("$REQUEST_TYPE IN (${requestTypes.joinToString(",") { "?" }})")
      selectionArgs.addAll(requestTypes)
    }

    // Append AND if both request types and response statuses are specified
    if (requestTypes.isNotEmpty() && responseStatuses.isNotEmpty()) {
      selection.append(" AND ")
    }

    // Build the selection clause based on response statuses
    if (responseStatuses.isNotEmpty()) {
      selection.append("$RESPONSE_STATUS IN (${responseStatuses.joinToString(",") { "?" }})")
      selectionArgs.addAll(responseStatuses)
    }

    val cursor = db.query(
      TABLE_NAME,
      null,
      selection.toString(),
      selectionArgs.toTypedArray(),
      null,
      null,
      null
    )
    cursor.use {
      while (it.moveToNext()) {
        apiLogs.add(it.toDomain())
      }
    }
    return apiLogs
  }



  private fun fillValues(response: ResponseDb): ContentValues {
    return ContentValues().apply {
      put(REQUEST_URL, response.requestUrl)
      put(REQUEST_BODY, response.requestBody)
      put(RESPONSE_BODY, response.responseBody)
      put(REQUEST_TYPE, response.requestType)
      put(EXECUTION_TIME, response.executionTime)
      put(RESPONSE_STATUS, response.responseStatus)
      put(RESPONSE_STATUS_CODE , response.responseStatusCode)
      put(RESPONSE_HEADERS , response.responseHeaders)
      put(ERROR , response.error)
      put(QUERY_PARAMETERS , response.queryParameters)
      put(REQUEST_HEADERS , response.requestHeaders)
      put(FILE_PATH , response.filePath)


    }
  }
}
