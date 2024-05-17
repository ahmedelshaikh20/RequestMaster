package com.example.instabugtask.ui.screens.main

sealed class MainScreenUiEvents {


  data class UrlChanged(val url: String) : MainScreenUiEvents()
  data class HeaderKeyChanged(val headerKey: String) : MainScreenUiEvents()
  data class HeaderValueChanged(val headerValue: String) : MainScreenUiEvents()
  data class RequestTypeChanged(val requestType: String) : MainScreenUiEvents()
  data class QueryParameterChanged(val queryParameter: String) : MainScreenUiEvents()
  data class QueryValueChanged(val queryValue: String) : MainScreenUiEvents()
  data class FileNameChanged(val filePath: String) : MainScreenUiEvents()
  data class JsonKeyChange(val jsonKey: String) : MainScreenUiEvents()
  data class JsonValueChanged(val jsonValue: String) : MainScreenUiEvents()
  data class onFileChossen(val filePath: String) : MainScreenUiEvents()


  // ---
  data object OnAddHeaderClick:MainScreenUiEvents()
  data object OnExecuteClick:MainScreenUiEvents()
  data object OnClearClick:MainScreenUiEvents()
  data object OnAddQueryClick:MainScreenUiEvents()
  data object OnAddJsonClick:MainScreenUiEvents()
  data object OnAddJsonButtonClicked:MainScreenUiEvents()
  data object OnAddParameterButtonClicked:MainScreenUiEvents()
  data object OnChooseFileButtonClicked:MainScreenUiEvents()


}


