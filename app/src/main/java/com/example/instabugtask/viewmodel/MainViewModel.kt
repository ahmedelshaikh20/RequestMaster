package com.example.instabugtask.viewmodel

import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instabugtask.api.model.Response
import com.example.instabugtask.api.model.toDomain
import com.example.instabugtask.domain.repository.Repository
import com.example.instabugtask.ui.screens.main.MainScreenUiEvents
import com.example.instabugtask.ui.screens.main.getEmptyState
import com.example.instabugtask.ui.screens.main.model.RequestType
import com.example.instabugtask.utils.threadutils.runInBackground
import com.example.instabugtask.utils.threadutils.runInUiThread
import org.json.JSONObject
import java.net.UnknownHostException
import kotlin.concurrent.thread


class MainViewModel(private val repository: Repository) :
  ViewModel() {

  var state by mutableStateOf(getEmptyState())


  fun onEvent(event: MainScreenUiEvents) {
    when (event) {
      is MainScreenUiEvents.HeaderKeyChanged -> {
        state = state.copy(currentKey = event.headerKey)
      }

      is MainScreenUiEvents.HeaderValueChanged -> {
        state = state.copy(currentHeader = event.headerValue)
      }

      MainScreenUiEvents.OnAddHeaderClick -> {
        val updatedHeader = state.headers + (state.currentKey to state.currentHeader)
        state = state.copy(headers = updatedHeader)
      }

      MainScreenUiEvents.OnClearClick -> {
        state = getEmptyState()
      }

      MainScreenUiEvents.OnExecuteClick -> {
        Log.d("New Url", "${state.url} is ${URLUtil.isValidUrl(state.url)} ")
        when (state.requestType) {
          RequestType.GetRequest -> {


            if (!URLUtil.isValidUrl(state.url)) {
              throw Exception("Invalid Url")
            }

            runInBackground {
              try {
                val res =
                  repository.executeGetApiRequest(
                    StringBuilder(state.url),
                    state.headers,
                    state.querys
                  )
                runInUiThread {
                  state = state.copy(currentRequest = res)
                }
              } catch (e: UnknownHostException) {
                state = state.copy(
                  showToastMessage = true,
                  currentToastMessage = "Failed Get :  UnKnown Host"
                )
              } catch (e: Exception) {
                state =
                  state.copy(showToastMessage = true, currentToastMessage = "Error : ${e.message}")
              }

            }


          }

          RequestType.PostRequest -> {
            if (!isValidUrl(state.url)) {
              throw Exception("Invalid Url")
            }

            runInBackground {
              try {
                state.requestBodyMap
                val res = repository.executePostApiRequest(
                  (state.url),
                  state.headers,
                  JSONObject(state.requestBodyMap)
                )
                runInUiThread {
                  state = state.copy(
                    showToastMessage = true,
                    currentToastMessage = "Success Post Request",
                    currentRequest = res
                  )
                }
              } catch (e: UnknownHostException) {
                state = state.copy(
                  showToastMessage = true,
                  currentToastMessage = "Failed Post :  UnKnown Host"
                )
              } catch (e: Exception) {
                state = state.copy(
                  showToastMessage = true,
                  currentToastMessage = "Error Post : ${e.message}"
                )
              }
            }
          }

          RequestType.PostWithFileRequest -> {

            if (!isValidUrl(state.url)) {
              throw Exception("Invalid Url")
            }
            runInBackground {
              try {
                val res =
                  repository.executePostApiRequestWithFile(state.url, state.filePath, state.headers)
                runInUiThread {
                  state = state.copy(
                    showToastMessage = true,
                    currentToastMessage = "Success Post Request",
                    currentRequest = res
                  )
                }
              } catch (e: UnknownHostException) {
                state = state.copy(
                  showToastMessage = true,
                  currentToastMessage = "Failed Post :  UnKnown Host"
                )
              } catch (e: Exception) {
                state = state.copy(
                  showToastMessage = true,
                  currentToastMessage = "Failed Post Request : ${e.message}",
                )
              }
            }
          }
        }
      }


      is MainScreenUiEvents.RequestTypeChanged -> {
        state = state.copy(
          requestType = RequestType.fromString(event.requestType),
          isChooseFileClicked = false,
          isAddParameterClicked = false,
          isAddJsonBodyClicked = false,
          headers = mapOf(),
          querys = mapOf(),
          filePath = null,

          )
      }

      is MainScreenUiEvents.UrlChanged -> {
        state = state.copy(url = event.url)

      }

      is MainScreenUiEvents.FileNameChanged -> {
        state = state.copy(filePath = event.filePath)
      }

      is MainScreenUiEvents.QueryParameterChanged -> {
        state = state.copy(queryParameter = event.queryParameter)
      }

      is MainScreenUiEvents.QueryValueChanged -> {
        state = state.copy(queryValue = event.queryValue)
      }

      MainScreenUiEvents.OnAddQueryClick -> {
        val updatedQuerys = state.querys + (state.queryParameter to state.queryValue)
        state = state.copy(querys = updatedQuerys, queryParameter = "", queryValue = "")
      }

      is MainScreenUiEvents.JsonKeyChange -> {
        state = state.copy(currentJsonKey = event.jsonKey)
      }

      is MainScreenUiEvents.JsonValueChanged -> {
        state = state.copy(currentJsonValue = event.jsonValue)
      }

      MainScreenUiEvents.OnAddJsonClick -> {
        val newRequestBodyMap =
          state.requestBodyMap + (state.currentJsonKey to state.currentJsonValue)
        state =
          state.copy(requestBodyMap = newRequestBodyMap, currentJsonValue = "", currentJsonKey = "")
      }

      MainScreenUiEvents.OnAddJsonButtonClicked -> {
        state = state.copy(isAddJsonBodyClicked = !state.isAddJsonBodyClicked)
      }

      MainScreenUiEvents.OnAddParameterButtonClicked -> {
        state = state.copy(isAddParameterClicked = !state.isAddParameterClicked)
      }

      MainScreenUiEvents.OnChooseFileButtonClicked -> {
        state = state.copy(isChooseFileClicked = !state.isAddJsonBodyClicked)
      }

      is MainScreenUiEvents.onFileChossen -> {
        state = state.copy(filePath = event.filePath)
        Log.d("Current Path", state.filePath.toString())
      }
    }
  }

  fun isValidUrl(url: String): Boolean {
    val regex = Regex(
      "^(https?://)?([a-zA-Z0-9]+(-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]{2,}(/\\S*)?$",
      RegexOption.IGNORE_CASE
    )
    return regex.matches(url)
  }

  fun clearToastMessage() {
    state = state.copy(showToastMessage = false, currentToastMessage = null)
  }

  fun clear() {
    state = getEmptyState()
  }

}

class MainViewModelFactory(
  private val repository: Repository,
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (MainViewModel(repository) as T)
}

