package com.example.instabugtask.ui.screens.main.model

sealed class RequestType(val request: String) {
  object GetRequest : RequestType("get")
  object PostRequest : RequestType("post")
  object PostWithFileRequest : RequestType("post_with_file")

  companion object {
    fun fromString(type: String): RequestType {
      return when (type) {
        "get" -> GetRequest
        "post" -> PostRequest
        "post_with_file" -> PostWithFileRequest
        else -> GetRequest
      }

    }
  }
}
