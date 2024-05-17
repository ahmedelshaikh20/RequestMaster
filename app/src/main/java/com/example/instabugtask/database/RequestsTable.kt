package com.example.instabugtask.database

object RequestsTable {

  const val DB_NAME = "MY DB"
  const val DB_VERSION = 5

  // Then We Specify the names of the columns
  const val TABLE_NAME = "API_TABLE"
  const val ID = "id"
  const val REQUEST_URL = "request_url"
  const val REQUEST_BODY = "request_body"
  const val RESPONSE_STATUS = "response_status"
  const val RESPONSE_BODY = "response_body"
  const val REQUEST_TYPE = "request_type"
  const val EXECUTION_TIME = "execution_time"
  const val RESPONSE_STATUS_CODE = "response_status_code"
  const val REQUEST_HEADERS = "request_headers"
  const val RESPONSE_HEADERS = "response_headers"
  const val QUERY_PARAMETERS = "query_parameters"
  const val ERROR = "error"
  const val FILE_PATH = "file_path"




  //Table Creation
  val CREATE_TABLE =
    "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY , $REQUEST_TYPE TEXT , $REQUEST_URL TEXT,$REQUEST_BODY TEXT , $REQUEST_HEADERS TEXT ,$QUERY_PARAMETERS TEXT ,$RESPONSE_STATUS TEXT , $RESPONSE_BODY TEXT , $RESPONSE_HEADERS TEXT , $EXECUTION_TIME TEXT,$RESPONSE_STATUS_CODE Int ,$ERROR TEXT , $FILE_PATH TEXT)"
  // Drop Table
  val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
