package com.example.instabugtask.utils.model

import android.net.Uri
import java.io.InputStream

data class FileDetails(
  var fileUri: Uri,
  var fileStream: InputStream,
  var fileMime: String
)

