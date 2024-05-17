package com.example.instabugtask.utils.threadutils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

private val executors = Executors.newCachedThreadPool()
private val uiHandler = Handler(Looper.getMainLooper())


fun runInBackground(block: () -> Unit) {
  executors.submit(block)
}

fun runInUiThread(block: () -> Unit) {
  uiHandler.post(block)
}
