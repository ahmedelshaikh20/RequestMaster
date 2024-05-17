package com.example.instabugtask.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instabugtask.domain.repository.Repository
import com.example.instabugtask.domain.model.ResponseInfo
import com.example.instabugtask.ui.screens.logscreen.LogScreenEvents
import com.example.instabugtask.ui.screens.logscreen.getEmptyLogState
import com.example.instabugtask.utils.threadutils.runInBackground
import com.example.instabugtask.utils.threadutils.runInUiThread


class LogsViewModel(private val repository: Repository) : ViewModel() {

  var state by mutableStateOf(getEmptyLogState())

  fun onEvent(event: LogScreenEvents) {
    when (event) {
      LogScreenEvents.OnAscendingOrderClick -> {
        state = if (!state.isAscendingClicked)
          state.copy(isAscendingClicked = !state.isAscendingClicked, isDescendingClicked = false)
        else
          state.copy(isAscendingClicked = !state.isAscendingClicked)


      }

      LogScreenEvents.OnDescendingOrderClick -> {
        state = if (!state.isDescendingClicked)
          state.copy(isDescendingClicked = !state.isDescendingClicked, isAscendingClicked = false)
        else
          state.copy(isDescendingClicked = !state.isDescendingClicked)
      }

      LogScreenEvents.OnFailClickClick -> {
        state = state.copy(isFailureChecked = !state.isFailureChecked)
        state = if (state.isFailureChecked) {
          state.copy(selectedStatus = state.selectedStatus + "Fail")
        } else {
          state.copy(selectedStatus = state.selectedStatus - "Fail")
        }
      }

      LogScreenEvents.OnGetClick -> {
        state = state.copy(isGetChecked = !state.isGetChecked)
        state = if (state.isGetChecked) {
          state.copy(selectedRequestTypes = state.selectedRequestTypes + "GET")
        } else {
          state.copy(selectedRequestTypes = state.selectedRequestTypes - "GET")
        }


      }

      LogScreenEvents.OnPostClick -> {
        state = state.copy(isPostChecked = !state.isPostChecked)
        state = if (state.isPostChecked) {
          state.copy(selectedRequestTypes = state.selectedRequestTypes + "POST")
        } else {
          state.copy(selectedRequestTypes = state.selectedRequestTypes - "POST")
        }
      }

      LogScreenEvents.OnSuccessClick -> {
        state = state.copy(isSuccessChecked = !state.isSuccessChecked)
        state = if (state.isSuccessChecked) {
          state.copy(selectedStatus = state.selectedStatus + "Success")
        } else {
          state.copy(selectedStatus = state.selectedStatus - "Success")
        }
      }

      LogScreenEvents.OnRunClick -> {
        //Filtration
        runInBackground {
          val res = repository.filterLogs(state.selectedRequestTypes, state.selectedStatus)
          val sortedRes =sortByExecutionTime(res)
          runInUiThread {
            state = state.copy(currentLogs = sortedRes)
          }

        }

      }
    }
  }

  init {
   // getAllLogs()
  }

  private fun sortByExecutionTime(res: List<ResponseInfo>): List<ResponseInfo> {
    return if (state.isAscendingClicked) {
      res.sortedBy { it.executionTime?.toInt() }
    } else if (state.isDescendingClicked) {
      res.sortedByDescending { it.executionTime?.toInt() }
    } else {
      res
    }
  }


  fun getAllLogs() {
    runInBackground {
      val res = repository.getAllLogs()
      runInUiThread {
        state = state.copy(currentLogs = res)
      }

    }
  }


}

class LogsViewModelFactory(
  private val repository: Repository,
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel> create(modelClass: Class<T>) =
    (LogsViewModel(repository) as T)
}
