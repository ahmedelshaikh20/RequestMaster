package com.example.instabugtask.ui.screens.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instabugtask.R
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
import java.io.File

@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {
  val context = LocalContext.current
  val filePickerLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

      viewModel.onEvent(
        MainScreenUiEvents.onFileChossen(
         File(uri?.path).absolutePath
        )
      )
      Log.d("MainScreen" , viewModel.state.filePath.toString() )
    }

  val state = viewModel.state
  val requestPermissionLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        filePickerLauncher.launch("*/*")

      } else {
        Toast.makeText(context,"Storage Permission Needed", Toast.LENGTH_SHORT).show()

      }
    }
  val openFileLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

  LaunchedEffect(key1 = viewModel.state.showToastMessage) {
    if (state.showToastMessage) {
      Toast.makeText(context, state.currentToastMessage, Toast.LENGTH_SHORT).show()
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
    AddHeaders(
      headerKey = state.currentKey,
      headerValue = state.currentHeader,
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
      onHeaderKeyChange = { viewModel.onEvent(MainScreenUiEvents.HeaderKeyChanged(it)) },
      onHeaderValueChanged = { viewModel.onEvent(MainScreenUiEvents.HeaderValueChanged(it)) },
      onAddHeaderClick = { viewModel.onEvent(MainScreenUiEvents.OnAddHeaderClick) },
      addedHeaders = state.headers
    )
    AnimatedVisibility(visible = state.requestType == RequestType.GetRequest) {

      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        ActionButton(
          onClick = { viewModel.onEvent(MainScreenUiEvents.OnAddParameterButtonClicked) },
          text = stringResource(R.string.add_parameters_button),
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        if (state.isAddParameterClicked) {
          PopupView(
            modifier = Modifier.padding(10.dp),
            onClose = { viewModel.onEvent(MainScreenUiEvents.OnAddParameterButtonClicked) }) {
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
      }
    }

    AnimatedVisibility(visible = state.requestType == RequestType.PostRequest) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        ActionButton(
          onClick = { viewModel.onEvent(MainScreenUiEvents.OnAddJsonButtonClicked) },
          text = stringResource(R.string.add_json_body),
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (state.isAddJsonBodyClicked) {
          PopupView(
            modifier = Modifier.padding(10.dp),
            onClose = { viewModel.onEvent(MainScreenUiEvents.OnAddJsonButtonClicked) }) {


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
    }
    AnimatedVisibility(visible = state.requestType == RequestType.PostWithFileRequest) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ActionButton(
          onClick = {
            (requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE))
          },
          text = stringResource(R.string.add_file),
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
    ExpandableCard(
      state.currentRequest,
      onFilePathClicked = { openFile(context, it, openFileLauncher) },
      modifier = Modifier
        .padding(10.dp)
        .testTag(stringResource(id = R.string.request_result)),

      )
    BasicButton(
      value = "Show Logs",
      onClick = { navController.navigate(Screen.LogScreen.route) },
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )


  }

}

private fun openFile(
  context: Context,
  filePath: String,
  openFileLauncher: ActivityResultLauncher<Intent>
) {
  val fileUri = Uri.parse(filePath)
  val openFileIntent = Intent(Intent.ACTION_VIEW, fileUri).apply {
    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
  }
  openFileLauncher.launch(openFileIntent)
}
