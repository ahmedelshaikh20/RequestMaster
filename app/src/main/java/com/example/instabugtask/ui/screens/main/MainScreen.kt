package com.example.instabugtask.ui.screens.main

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instabugtask.navigation.Screen
import com.example.instabugtask.ui.screens.components.ActionButton
import com.example.instabugtask.ui.screens.components.AddHeaders
import com.example.instabugtask.ui.screens.components.AddRequestBody
import com.example.instabugtask.ui.screens.components.AddRequestParameters
import com.example.instabugtask.ui.screens.components.BasicButton
import com.example.instabugtask.ui.screens.components.CurlInputField
import com.example.instabugtask.ui.screens.components.ExpandableCard
import com.example.instabugtask.ui.screens.components.PopupView
import com.example.instabugtask.ui.screens.components.RequestChooseField
import com.example.instabugtask.ui.screens.components.RunButton
import com.example.instabugtask.ui.screens.main.model.RequestType
import com.example.instabugtask.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {

  val state = viewModel.state

  val context = LocalContext.current
  val filePickerLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
      viewModel.onEvent(MainScreenUiEvents.onFileChossen(uri?.lastPathSegment?.removePrefix("raw:").toString()))
    }

LaunchedEffect(key1 = viewModel.state.showToastMessage ){
  if (state.showToastMessage){
    Toast.makeText(context , state.currentToastMessage , Toast.LENGTH_SHORT).show()
    viewModel.clearToastMessage()
  }
}
  Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
  ) {
    CurlInputField(modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp),
      url = state.url,
      onUrlChange = { viewModel.onEvent(MainScreenUiEvents.UrlChanged(it)) })
    RequestChooseField(
      modifier = Modifier.padding(top = 10.dp),
      onClick = { viewModel.onEvent(MainScreenUiEvents.RequestTypeChanged(it)) },
      state.requestType
    )
    AddHeaders(headerKey = state.currentKey,
      headerValue = state.currentHeader,
      modifier = Modifier.padding(10.dp),
      onHeaderKeyChange = { viewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(it)) },
      onHeaderValueChanged = { viewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(it)) },
      onAddHeaderClick = { viewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick) },
      addedHeaders = state.headers)
    AnimatedVisibility(visible = state.requestType == RequestType.GetRequest) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ActionButton(
          onClick = { viewModel.onEvent(MainScreenUiEvents.OnAddParameterButtonClicked) },
          text = "Add Parameters",
          modifier = Modifier.align(Alignment.CenterVertically)
        )
      }
    }
    if (state.isAddParameterClicked) {
      PopupView(onClose = { viewModel.onEvent(MainScreenUiEvents.OnAddParameterButtonClicked) }) {
        AddRequestParameters(queryValue = state.queryValue,
          queryParam = state.queryParameter,
          modifier = Modifier.padding(10.dp),
          onQueryParameterChange = {
            viewModel.onEvent(
              MainScreenUiEvents.QueryParameterChanged(
                it
              )
            )
          },
          onQueryValueChange = { viewModel.onEvent(MainScreenUiEvents.QueryValueChanged(it)) },
          onAddClick = { viewModel.onEvent(MainScreenUiEvents.OnAddQueryClick) })
      }
    }

    AnimatedVisibility(visible = state.requestType == RequestType.PostRequest) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ActionButton(
          onClick = { viewModel.onEvent(MainScreenUiEvents.OnAddJsonButtonClicked) },
          text = "Add Json Body",
          modifier = Modifier.align(Alignment.CenterVertically)
        )
      }
      if (state.isAddJsonBodyClicked) {
        PopupView(onClose = { viewModel.onEvent(MainScreenUiEvents.OnAddJsonButtonClicked) }) {


          AddRequestBody(jsonKey = state.currentJsonKey,
            jsonValue = state.currentJsonValue,
            modifier = Modifier.padding(10.dp),
            onJsonKeyChange = {
              viewModel.onEvent(
                MainScreenUiEvents.JsonKeyChange(
                  it
                )
              )
            },
            onJsonValueChange = { viewModel.onEvent(MainScreenUiEvents.JsonValueChanged(it)) },
            onAddClick = { viewModel.onEvent(MainScreenUiEvents.OnAddJsonClick) })
        }
      }
    }
    AnimatedVisibility(visible = state.requestType == RequestType.PostWithFileRequest) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ActionButton(
          onClick = { filePickerLauncher.launch("*/*") },
          text = "Add File",
          modifier = Modifier.align(Alignment.CenterVertically)
        )
      }

    }
    RunButton(
      onRunClick = {
        viewModel.onEvent(MainScreenUiEvents.OnExecuteClick)
      },
      onClearClick = { viewModel.onEvent(MainScreenUiEvents.OnClearClick) },
      modifier = Modifier.padding(10.dp)
    )
    ExpandableCard(state.currentRequest, modifier = Modifier.padding(10.dp))
    BasicButton(
      value = "Show Logs",
      onClick = { navController.navigate(Screen.LogScreen.route) },
      modifier = Modifier.align(Alignment.CenterHorizontally))


  }

}


