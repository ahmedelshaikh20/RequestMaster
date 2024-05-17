package com.example.instabugtask.ui.screens.logscreen

import com.example.instabugtask.domain.model.ResponseInfo

data class LogScreenState(
  val isGetChecked: Boolean,
  val isPostChecked: Boolean,
  val isSuccessChecked: Boolean,
  val isFailureChecked: Boolean,
  val isAscendingClicked: Boolean,
  val isDescendingClicked: Boolean,

  val selectedRequestTypes : List<String>,
  val selectedStatus : List<String>,

  val currentLogs: List<ResponseInfo>
)


fun getEmptyLogState(): LogScreenState {
  return LogScreenState(false, false, false, false , false , false, emptyList() , emptyList() , emptyList())
}
