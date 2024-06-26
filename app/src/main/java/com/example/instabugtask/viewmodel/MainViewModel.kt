package com.example.instabugtask.viewmodel

import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instabugtask.domain.repository.Repository
import com.example.instabugtask.ui.screens.main.MainScreenUiEvents
import com.example.instabugtask.ui.screens.main.getEmptyState
import com.example.instabugtask.ui.screens.main.model.RequestType
import com.example.instabugtask.utils.model.FileDetails
import com.example.instabugtask.utils.networkutils.NetworkUtils
import com.example.instabugtask.utils.threadutils.runInBackground
import com.example.instabugtask.utils.threadutils.runInUiThread
import org.json.JSONObject
import java.io.InputStream
import java.net.UnknownHostException


class MainViewModel(private val repository: Repository) :
  ViewModel() {

  var state by mutableStateOf(getEmptyState())
  var currentFileDetails : FileDetails? =null

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

        when (state.requestType) {
          RequestType.GetRequest -> {


            if (!isValidUrl(state.url)) {
              return showErrorMessage("Invalid Url")
            }

            runInBackground {
              try {
                if (!NetworkUtils.checkNetworkAvailability())
                  return@runInBackground showErrorMessage("Check Network Connection")
                val res =
                  repository.executeGetApiRequest(
                    StringBuilder(state.url),
                    state.headers,
                    state.queries
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
              return showErrorMessage("Invalid Url")
            }

            runInBackground {
              try {
                if (!NetworkUtils.checkNetworkAvailability())
                  return@runInBackground showErrorMessage("Check Network Connection")
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
                showErrorMessage("Failed Post :  UnKnown Host")
              } catch (e: Throwable) {
                showErrorMessage(e.message.toString())
              }
            }
          }

          RequestType.PostWithFileRequest -> {

            if (!isValidUrl(state.url)) {
              return showErrorMessage("Invalid Url")
            }

            runInBackground {
              try {
                val fileDetails = currentFileDetails // Store currentFileDetails in a temporary variable
                if (!NetworkUtils.checkNetworkAvailability())
                  return@runInBackground showErrorMessage("Check Network Connection")
                if (fileDetails==null)
                  return@runInBackground showErrorMessage("Select File to Proceed")
                else {
                val res = repository.executePostApiRequestWithFile(
                      state.url,
                      fileDetails.fileStream,
                      state.headers,
                      fileDetails.fileMime,
                      fileDetails.fileUri
                    )

                runInUiThread {
                  state = state.copy(
                    showToastMessage = true,
                    currentToastMessage = "Success Post Request",
                    currentRequest = res
                  )
                }}
              } catch (e: UnknownHostException) {
                showErrorMessage("Failed Post :  UnKnown Host")
              } catch (e: Throwable) {
                showErrorMessage(e.message.toString())
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
          queries = mapOf(),
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
        val updatedQuerys = state.queries + (state.queryParameter to state.queryValue)
        state = state.copy(queries = updatedQuerys, queryParameter = "", queryValue = "")
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
       currentFileDetails = FileDetails(fileStream = event.fileStream , fileMime = event.mime , fileUri = event.uri)
        state =state.copy(showToastMessage = true , currentToastMessage = "File Selected Successfully")
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

  fun showErrorMessage(message: String) {
    state = state.copy(
      showToastMessage = true,
      currentToastMessage = "ERROR : ${message}",
    )
  }

}

class MainViewModelFactory(
  private val repository: Repository,
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (MainViewModel(repository) as T)
}

